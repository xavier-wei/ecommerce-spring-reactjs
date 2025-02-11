-- password: admin123
insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, activation_code, active, password, password_reset_code, provider)
    values(1, 'admin@gmail.com', 'Admin', 'Admin', null, null, null, null, null, true, '{bcrypt}$2a$10$kQ7xG2AaH8Z4CEiyZGgEzePWsTwkCNOZ.3oAVe0Qy9TbeZJLTQkD6', null, 'LOCAL');

-- password: admin123
insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, activation_code, active, password, password_reset_code, provider)
    values(122, 'test123@test.com', 'John', 'Doe', 'New York', 'Wall Street 1', '1234567890', '1234567890', null, true, '{bcrypt}$2a$10$kQ7xG2AaH8Z4CEiyZGgEzePWsTwkCNOZ.3oAVe0Qy9TbeZJLTQkD6', null, 'LOCAL');

insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, activation_code, active, password, password_reset_code, provider)
    values (126, 'xavier.liu@keywaycloud.com.tw', 'John2', 'Doe2', 'New York', 'Wall Street1', '1234567890', '1234567890','8e97dc37-2cf5-47e2-98e0',false,'{bcrypt}$2a$10$itAhQBotR3ZZOEJtmZV2/eLIS94Sw/o.ynvfl9HYCLpcyvCjTG6eC','3f9bcdb0-2241-4c34-803e-598b497d571f', 'LOCAL');

insert into user_role (user_id, roles) values (1, 'ADMIN');
insert into user_role (user_id, roles) values (122, 'USER');
insert into user_role (user_id, roles) values (126, 'USER');

