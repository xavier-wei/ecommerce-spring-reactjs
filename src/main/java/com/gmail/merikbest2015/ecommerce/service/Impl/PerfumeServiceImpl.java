package com.gmail.merikbest2015.ecommerce.service.Impl;


import com.gmail.merikbest2015.ecommerce.domain.Perfume;
import com.gmail.merikbest2015.ecommerce.dto.perfume.PerfumeSearchRequest;
import com.gmail.merikbest2015.ecommerce.enums.SearchPerfume;
import com.gmail.merikbest2015.ecommerce.exception.ApiRequestException;
import com.gmail.merikbest2015.ecommerce.repository.PerfumeRepository;
import com.gmail.merikbest2015.ecommerce.repository.projection.PerfumeProjection;
import com.gmail.merikbest2015.ecommerce.service.PerfumeService;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import software.amazon.awssdk.services.s3.S3Client;

import static com.gmail.merikbest2015.ecommerce.constants.ErrorMessage.PERFUME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PerfumeServiceImpl implements PerfumeService {

    private final PerfumeRepository perfumeRepository;

    private final S3Client s3Client;


    @Value("${amazon.s3.bucket.name}")
    private String bucketName;

    @Value("${amazon.s3.region:eu-central-1}") // 預設區域為 eu-central-1
    private String awsRegion;

    @Override
    public Perfume getPerfumeById(Long perfumeId) {
        return perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new ApiRequestException(PERFUME_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<PerfumeProjection> getAllPerfumes(Pageable pageable) {
        return perfumeRepository.findAllByOrderByIdAsc(pageable);
    }

    @Override
    public List<PerfumeProjection> getPerfumesByIds(List<Long> perfumesId) {
        return perfumeRepository.getPerfumesByIds(perfumesId);
    }

    @Override
    public Page<PerfumeProjection> findPerfumesByFilterParams(PerfumeSearchRequest filter, Pageable pageable) {

        List<String> safePerfumers = (filter.getPerfumers() == null || filter.getPerfumers().isEmpty())
                ? Collections.emptyList()
                : filter.getPerfumers();
        List<String> safeGenders = (filter.getGenders() == null || filter.getGenders().isEmpty())
                ? Collections.emptyList()
                : filter.getGenders();

        return perfumeRepository.findPerfumesByFilterParams(
                safePerfumers,
                safePerfumers.size(),
                safeGenders,
                safeGenders.size(),
                filter.getPrices().get(0),
                filter.getPrices().get(1),
                filter.getSortByPrice(),
                pageable);
    }


    @Override
    public List<Perfume> findByPerfumer(String perfumer) {
        return perfumeRepository.findByPerfumerOrderByPriceDesc(perfumer);
    }

    @Override
    public List<Perfume> findByPerfumeGender(String perfumeGender) {
        return perfumeRepository.findByPerfumeGenderOrderByPriceDesc(perfumeGender);
    }

    @Override
    public Page<PerfumeProjection> findByInputText(SearchPerfume searchType, String text, Pageable pageable) {
        if (searchType.equals(SearchPerfume.BRAND)) {
            return perfumeRepository.findByPerfumer(text, pageable);
        } else if (searchType.equals(SearchPerfume.PERFUME_TITLE)) {
            return perfumeRepository.findByPerfumeTitle(text, pageable);
        } else {
            return perfumeRepository.findByManufacturerCountry(text, pageable);
        }
    }

    @Override
    @Transactional
    public Perfume savePerfume(Perfume perfume, MultipartFile multipartFile) {
        // 如果 multipartFile 為空，設置預設文件 URL
        if (multipartFile == null || multipartFile.isEmpty()) {
            String defaultFileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, awsRegion, "empty.jpg");
            perfume.setFilename(defaultFileUrl);
        } else {
            // 創建臨時文件以存儲 MultipartFile
            File tempFile = null;
            try {
                // 獲取文件原始名稱並創建唯一的文件名
                String originalFilename = multipartFile.getOriginalFilename();
                String fileExtension = originalFilename != null && originalFilename.contains(".")
                        ? originalFilename.substring(originalFilename.lastIndexOf("."))
                        : "";
                String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

                // 將 MultipartFile 寫入臨時文件
                tempFile = File.createTempFile("s3-upload-", fileExtension);
                multipartFile.transferTo(tempFile);

                // 上傳到 S3
                s3Client.putObject(builder -> builder
                                .bucket(bucketName)
                                .key(uniqueFilename),
                        RequestBody.fromFile(tempFile));

                // 設置 Perfume 的文件 URL
                String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, awsRegion, uniqueFilename);
                perfume.setFilename(fileUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to process and upload the file to S3", e);
            } finally {
                // 刪除臨時文件，避免資源洩漏
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }

        // 保存 Perfume 到資料庫
        return perfumeRepository.save(perfume);
    }

    @Override
    @Transactional
    public String deletePerfume(Long perfumeId) {
        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new ApiRequestException(PERFUME_NOT_FOUND, HttpStatus.NOT_FOUND));
        perfumeRepository.delete(perfume);
        return "Perfume deleted successfully";
    }

    @Override
    public DataFetcher<Perfume> getPerfumeByQuery() {
        return environment -> {
            // 從環境中獲取請求參數 "id"
            Long perfumeId = Long.parseLong(environment.getArgument("id"));
            // 使用 Optional 的方式處理查詢結果
            return perfumeRepository.findById(perfumeId)
                    .orElseThrow(() -> new RuntimeException("Perfume not found with id: " + perfumeId));
        };
    }

    @Override
    public DataFetcher<List<PerfumeProjection>> getAllPerfumesByQuery() {
        return environment -> {
            // 獲取所有 PerfumeProjection，按 ID 升序排序
            return perfumeRepository.findAllByOrderByIdAsc();
        };
    }

    @Override
    public DataFetcher<List<Perfume>> getAllPerfumesByIdsQuery() {
        return environment -> {
            // 從環境中獲取請求參數 "ids" 並轉換為 List<Long>
            List<String> ids = environment.getArgument("ids");
            List<Long> perfumeIds = ids.stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            // 根據 ID 查詢對應的 Perfume
            return perfumeRepository.findByIdIn(perfumeIds);
        };
    }
}
