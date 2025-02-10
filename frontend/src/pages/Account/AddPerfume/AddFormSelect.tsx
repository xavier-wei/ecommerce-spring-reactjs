import React, { FC, ReactElement } from "react";
import { Form, Select, Typography } from "antd";

type PropsType = {
    title: string;
    name: string;
    error?: string;
    placeholder: string;
    disabled: boolean;
    values: Array<string>;
};

const AddFormSelect: FC<PropsType> = ({ title, name, error, placeholder, disabled, values }): ReactElement => {
    return (
        <>
            <Typography.Text>{title}</Typography.Text>
            <Form.Item
                name={name}
                help={error}
                validateStatus={error && "error"}
            >
                <Select
                    placeholder={placeholder}
                    disabled={disabled}
                    style={{ width: "100%" }}
                    allowClear
                >
                    {values.map((option, index) => (
                        <Select.Option key={index} value={option}>
                            {option}
                        </Select.Option>
                    ))}
                </Select>
            </Form.Item>
        </>
    );
};

export default AddFormSelect;