CREATE DATABASE banksystem;
USE banksystem;

CREATE TABLE branch (
    BranchId VARCHAR(40) PRIMARY KEY,
    BranchName VARCHAR(50) NOT NULL,
    PostalAddress TEXT NOT NULL
    );

CREATE TABLE employee (
    EmployeeId VARCHAR(40) ,
    FirstName varchar(50) NOT NULL,
    LastName varchar(50) NOT NULL,
    BranchID VARCHAR(40),
    Nic CHAR(10) NOT NULL,
    Email VARCHAR(250) NOT NULL,
    BirthDate DATE NOT NULL,
    PRIMARY KEY(EmployeeId),
    FOREIGN KEY (BranchID) REFERENCES branch (BranchID)
    );

CREATE TABLE employee_login (
    EmployeeID VARCHAR(40) NOT NULL,
    Username VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(500) NOT NULL,
    Status ENUM('1','0') NOT NULL,
    Type ENUM('Normal','BranchManager'),
    FOREIGN KEY (EmployeeID) REFERENCES employee(EmployeeId)
    );

CREATE TABLE branch_manager (
     ManagerId VARCHAR(40) PRIMARY KEY,
     EmployeeId VARCHAR(40) ,
     BranchID VARCHAR(40) ,
     FOREIGN KEY (EmployeeId) REFERENCES employee(EmployeeId),
     FOREIGN KEY (BranchId) REFERENCES branch(BranchId)
     );

CREATE TABLE branch_managers_history (
    ManagerId VARCHAR(40),
    BranchId VARCHAR(40),
    JoinedDate DATE ,
    LeaveDate DATE,
    PRIMARY KEY (ManagerId, BranchId, JoinedDate),
    FOREIGN KEY (ManagerId) REFERENCES branch_manager(ManagerId),
    FOREIGN KEY (BranchID) REFERENCES branch(BranchID)
    );

CREATE TABLE normal_employee (
    EmployeeId VARCHAR(40) PRIMARY KEY,
    FOREIGN KEY (EmployeeId) REFERENCES employee(EmployeeId)
    );

CREATE TABLE normal_employees_history (
    EmployeeId VARCHAR(40),
    BranchId VARCHAR(40),
    JoinedDate DATE,
    LeaveDate DATE,
    PRIMARY KEY (EmployeeId, BranchId, JoinedDate),
    FOREIGN KEY (EmployeeId) REFERENCES normal_employee(EmployeeId),
    FOREIGN KEY (BranchID) REFERENCES branch(BranchID)
    );

CREATE TABLE customer (
    CustomerId VARCHAR(40) PRIMARY KEY,
    CustomerType ENUM('Individual','Organization') NOT NULL,
    Email VARCHAR(250)
    );

CREATE TABLE individual (
    CustomerId VARCHAR(40) PRIMARY KEY,
    Nic CHAR(10) UNIQUE NOT NULL,
    FirstName varchar(50) NOT NULL,
    LastName varchar(50) NOT NULL,
    HouseNumber VARCHAR(50) NOT NULL,
    StreetOne VARCHAR(50) NOT NULL,
    StreetTwo VARCHAR(50),
    Town VARCHAR(50) NOT NULL,
    District VARCHAR(50) NOT NULL,
    PostalCode VARCHAR(50) NOT NULL,
    Gender ENUM('Male','Female') NOT NULL,
    Birthday DATE NOT NULL,
    FOREIGN KEY (CustomerId) REFERENCES customer(CustomerId)
    );

CREATE TABLE organization (
    CustomerId VARCHAR(40) PRIMARY KEY,
    OrganizationName VARCHAR(50) NOT NULL,
    AuthorizedPerson VARCHAR(50) NOT NULL,
    RegisterNumber VARCHAR(40) NOT NULL,
    BuildingNumber VARCHAR(40) NOT NULL,
    StreetOne VARCHAR(50) NOT NULL,
    StreetTwo VARCHAR(50),
    Town VARCHAR(50) NOT NULL,
    PostalCode VARCHAR(50) NOT NULL,
    TelephoneNumber CHAR(10) NOT NULL,
    FOREIGN KEY (CustomerId) REFERENCES customer(CustomerId)
    );

CREATE TABLE customer_online_account (
    AccountId VARCHAR(40) PRIMARY KEY,
    CustomerId VARCHAR(40) UNIQUE NOT NULL,
    Email VARCHAR(50) NOT NULL,
    FOREIGN KEY (CustomerId) REFERENCES customer(CustomerId)
    );

CREATE TABLE customer_login (
    AccountId VARCHAR(40) NOT NULL,
    Username VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(40) NOT NULL,
    FOREIGN KEY (AccountId) REFERENCES customer_online_account(AccountId)
    );

CREATE TABLE savings_accounts_type (
    AccountTypeId VARCHAR(40) PRIMARY KEY,
    AccountTypeName VARCHAR(50) NOT NULL,
    InterestRate DECIMAL(5,2) NOT NULL,
    MinimumAge INT NOT NULL,
    MaximumAge INT NOT NULL,
    MinimumBalance DECIMAL(10,2)
    );

CREATE TABLE savings_account (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    BranchId VARCHAR(40) NOT NULL,
    AccountTypeId VARCHAR(40) NOT NULL,
    CustomerId VARCHAR(40) NOT NULL,
    FOREIGN KEY (BranchId) REFERENCES branch(BranchId),
    FOREIGN KEY (AccountTypeId) REFERENCES savings_accounts_type(AccountTypeId),
    FOREIGN KEY (CustomerId) REFERENCES customer(CustomerId)
    );

CREATE TABLE debit_card (
    CardNumber VARCHAR(40) PRIMARY KEY,
    CardCode VARCHAR(40) UNIQUE NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    IssuedDate DATE NOT NULL,
    ExpiryDate DATE NOT NULL,
    FOREIGN KEY (AccountNumber) REFERENCES savings_account(AccountNumber)
    );

CREATE TABLE savings_withdraw (
    WithdrawId VARCHAR(40) PRIMARY KEY,
    EmployeeId VARCHAR(40) NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    WithdrawDate DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    Teller VARCHAR(50) NOT NULL,
    FOREIGN KEY(EmployeeId) REFERENCES employee(EmployeeId),
    FOREIGN KEY(AccountNumber) REFERENCES savings_account(AccountNumber)
    );

CREATE TABLE savings_deposit (
    DepositId VARCHAR(40) PRIMARY KEY,
    EmployeeId VARCHAR(40) NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    DepositDate DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    Teller VARCHAR(50) NOT NULL,
    FOREIGN KEY(EmployeeId) REFERENCES employee(EmployeeId),
    FOREIGN KEY(AccountNumber) REFERENCES savings_account(AccountNumber)
    );

CREATE TABLE fixed_deposit_account (
    FDAccountNumber VARCHAR(40) PRIMARY KEY,
    SavingsAccountNumber VARCHAR(40) NOT NULL,
    CustomerId VARCHAR(40) NOT NULL,
    InterestRate DECIMAL(10,2) NOT NULL,
    DepositAmount DECIMAL(10,2) NOT NULL,
    DepositDate DATE NOT NULL,
    MaturityDate DATE NOT NULL,
    FOREIGN KEY(SavingsAccountNumber) REFERENCES savings_account(AccountNumber),
    FOREIGN KEY(CustomerId) REFERENCES customer(CustomerId)
    );

CREATE TABLE current_account (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    BranchId VARCHAR(40) NOT NULL,
    CustomerId VARCHAR(40) NOT NULL,
    FOREIGN KEY (BranchId) REFERENCES branch(BranchId),
    FOREIGN KEY (CustomerId) REFERENCES customer(CustomerId)
    );

CREATE TABLE current_account_open (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    OpenEmployeeId VARCHAR(40) NOT NULL,
    OpenDate DATE NOT NULL,
    FOREIGN KEY (AccountNumber) REFERENCES current_account(AccountNumber),
    FOREIGN KEY (OpenEmployeeId) REFERENCES employee(EmployeeId)
    );

CREATE TABLE current_account_close (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    CloseEmployeeId VARCHAR(40) NOT NULL,
    CloseDate DATE NOT NULL,
    FOREIGN KEY (AccountNumber) REFERENCES current_account(AccountNumber),
    FOREIGN KEY (CloseEmployeeId) REFERENCES employee(EmployeeId)
    );

CREATE TABLE savings_open (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    OpenEmployeeId VARCHAR(40) NOT NULL,
    OpenDate DATE NOT NULL,
    FOREIGN KEY (AccountNumber) REFERENCES savings_account(AccountNumber),
    FOREIGN KEY (OpenEmployeeId) REFERENCES employee(EmployeeId)
    );

CREATE TABLE savings_close (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    CloseEmployeeId VARCHAR(40) NOT NULL,
    CloseDate DATE NOT NULL,
    FOREIGN KEY (AccountNumber) REFERENCES savings_account(AccountNumber),
    FOREIGN KEY (CloseEmployeeId) REFERENCES employee(EmployeeId)
    );

CREATE TABLE current_account_deposit (
    DepositId VARCHAR(40) PRIMARY KEY,
    EmployeeId VARCHAR(40) NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    DepositDate DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    ChequeNumber VARCHAR(40) NOT NULL,
    FOREIGN KEY(EmployeeId) REFERENCES employee(EmployeeId),
    FOREIGN KEY(AccountNumber) REFERENCES current_account(AccountNumber)
    );

CREATE TABLE current_account_withdraw (
    WithdrawId VARCHAR(40) PRIMARY KEY,
    EmployeeId VARCHAR(40) NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    WithdrawDate DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    ChequeNumber VARCHAR(40) NOT NULL,
    FOREIGN KEY(EmployeeId) REFERENCES employee(EmployeeId),
    FOREIGN KEY(AccountNumber) REFERENCES current_account(AccountNumber)
    );

CREATE TABLE transfer (
    TransferId VARCHAR(40) PRIMARY KEY,
    TransferredFromAccountNumber VARCHAR(40) NOT NULL,
    TransferredToAccountNumber VARCHAR(40) NOT NULL,
   	TransferAmount DECIMAL(10,2) NOT NULL,
    Date DATE NOT NULL,
    FOREIGN KEY(TransferredFromAccountNumber) REFERENCES savings_account(AccountNumber),
    FOREIGN KEY(TransferredToAccountNumber) REFERENCES savings_account(AccountNumber)
    );

CREATE TABLE atm_withdraw (
    WithdrawId VARCHAR(40) PRIMARY KEY,
    CardNumber VARCHAR(40) NOT NULL,
    WithdrawDate DATETIME NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY(CardNumber) REFERENCES debit_card(CardNumber)
    );