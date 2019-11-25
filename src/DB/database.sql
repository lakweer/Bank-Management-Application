create database bank;

create table employee (
    employee_id varchar(40),
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    nic char(10) not null,
    birth_date date not null,
    primary key(employee_id)
    );

create table branch (
    branch_id int(20),
    branch_name varchar(50) not null,
    manager_id varchar(40),
    postal_address blob not null,
    primary key(branch_id)
    );

create table employee_branch(
    branch_id int(20) ,
    employee_id varchar(40) ,
    joined_date Date,
    leave_date Date,
    primary key(branch_id, employee_id, joined_date),
    foreign key(branch_id) references branch(branch_id),
    foreign key(employee_id) references employee(employee_id)
    );

create table employee_users(
    employee_id varchar(40),
    username varchar(50),
    password varchar(300) not null,
    primary key(employee_id, username),
    foreign key(employee_id) references employee(employee_id)
    );