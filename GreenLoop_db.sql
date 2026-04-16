DROP DATABASE IF EXISTS greenloop;
CREATE DATABASE greenloop;
USE greenloop;

CREATE TABLE greenloop.customers
(
    customer_id int auto_increment PRIMARY KEY,
    name        varchar(100) not null,
    address     text null,
    mobile      varchar(20) null,
    email       varchar(100) null
);

CREATE TABLE greenloop.jobtype
(
    job_type_id int auto_increment PRIMARY KEY,
    type_name   varchar(50) not null
);

CREATE TABLE greenloop.orders
(
    order_id            int auto_increment PRIMARY KEY,
    customer_id         int null,
    order_date          date null,
    status              varchar(50) null,
    total_price         decimal(10, 2) null,
    is_repair           tinyint(1) null,
    repair_service_fee  decimal(10, 2) null,
    is_repaint          tinyint(1) null,
    repaint_service_fee decimal(10, 2) null,
    constraint orders_ibfk_1
        foreign key (customer_id) references greenloop.customers (customer_id)
);

CREATE INDEX customer_id ON greenloop.orders (customer_id);

CREATE TABLE greenloop.ordertype
(
    order_type_id int auto_increment PRIMARY KEY,
    type_name     varchar(50) not null
);

CREATE TABLE greenloop.properties
(
    property_key varchar(50) not null,
    value        varchar(50) not null,
    type         varchar(50) not null,
    constraint unique_property_key_type unique (property_key, type)
);

CREATE TABLE greenloop.roles
(
    role_id   int auto_increment PRIMARY KEY,
    role_name varchar(50) not null,
    constraint role_name unique (role_name)
);

CREATE TABLE greenloop.employees
(
    employee_id int auto_increment PRIMARY KEY,
    title       varchar(100) null,
    first_name  varchar(100) not null,
    last_name   varchar(100) not null,
    username    varchar(100) not null,
    address     text null,
    mobile      varchar(20) null,
    email       varchar(100) null,
    password    varchar(100) not null,
    schedule    text null,
    role_id     int not null,
    constraint username unique (username),
    constraint employees_ibfk_1 foreign key (role_id) references greenloop.roles (role_id)
);

CREATE INDEX role_id ON greenloop.employees (role_id);

CREATE TABLE greenloop.jobs
(
    job_id          int auto_increment PRIMARY KEY,
    order_id        int not null,
    employee_id     int null,
    job_description text null,
    start_date      date null,
    end_date        date null,
    status          varchar(50) null,
    assigned_date   date null,
    job_type_id     int not null,
    constraint unique_order_jobtype unique (order_id, job_type_id),
    constraint jobs_ibfk_1 foreign key (order_id) references greenloop.orders (order_id) on delete cascade,
    constraint jobs_ibfk_2 foreign key (employee_id) references greenloop.employees (employee_id),
    constraint jobs_ibfk_3 foreign key (job_type_id) references greenloop.jobtype (job_type_id)
);

CREATE INDEX employee_id ON greenloop.jobs (employee_id);
CREATE INDEX job_type_id ON greenloop.jobs (job_type_id);

CREATE TABLE greenloop.suppliers
(
    supplier_id   int auto_increment PRIMARY KEY,
    name          varchar(100) not null,
    contact_name  varchar(100) null,
    contact_email varchar(100) null,
    contact_phone varchar(20) null,
    address       text null
);

CREATE TABLE greenloop.parts
(
    part_id     int auto_increment PRIMARY KEY,
    name        varchar(100) not null,
    description text null,
    price       decimal(10, 2) null,
    supplier_id int null,
    constraint parts_ibfk_1 foreign key (supplier_id) references greenloop.suppliers (supplier_id)
);

CREATE TABLE greenloop.inventory
(
    inventory_id int auto_increment PRIMARY KEY,
    part_id      int null,
    quantity     int null,
    location     varchar(100) null,
    constraint inventory_ibfk_1 foreign key (part_id) references greenloop.parts (part_id)
);

CREATE INDEX part_id ON greenloop.inventory (part_id);

CREATE TABLE greenloop.notifications
(
    notification_id int auto_increment PRIMARY KEY,
    part_id         int null,
    min_quantity    int null,
    notify          tinyint(1) null,
    part_name       varchar(100) null,
    constraint unique_part_id unique (part_id),
    constraint notification_ibfk_1 foreign key (part_id) references greenloop.parts (part_id)
);

CREATE INDEX part_id ON greenloop.notifications (part_id);

CREATE TABLE greenloop.order_part
(
    order_part_id    int auto_increment PRIMARY KEY,
    sales_date       date null,
    part_id          int null,
    part_description text null,
    supplier_id      int null,
    quantity         int null,
    order_type_id    int null,
    price            decimal(10, 2) null,
    order_id         int null,
    constraint unique_order_part unique (order_id, part_id),
    constraint sales_fk_order_id foreign key (order_id) references greenloop.orders (order_id) on delete cascade,
    constraint sales_fk_order_type_id foreign key (order_type_id) references greenloop.ordertype (order_type_id),
    constraint sales_fk_product_id foreign key (part_id) references greenloop.parts (part_id),
    constraint sales_fk_supplier_id foreign key (supplier_id) references greenloop.suppliers (supplier_id)
);

CREATE INDEX supplier_id ON greenloop.parts (supplier_id);

INSERT IGNORE INTO greenloop.roles (role_name) VALUES ('Admin'), ('Employee'), ('Manager');

INSERT INTO greenloop.employees (title, first_name, last_name, username, address, mobile, email, password, schedule, role_id)
VALUES
('Mr', 'Rixy', 'Admin', 'rixy1', 'N/A', '0000000000', 'rixy1@example.com', 'Rixy@8248', 'Mon-Fri', (SELECT role_id FROM greenloop.roles WHERE role_name = 'Admin')),
('Mr', 'Rixy', 'Employee', 'rixy2', 'N/A', '0000000000', 'rixy2@example.com', 'Rixy@8248', 'Mon-Fri', (SELECT role_id FROM greenloop.roles WHERE role_name = 'Employee')),
('Mr', 'Rixy', 'Manager', 'rixy3', 'N/A', '0000000000', 'rixy3@example.com', 'Rixy@8248', 'Mon-Fri', (SELECT role_id FROM greenloop.roles WHERE role_name = 'Manager'));
