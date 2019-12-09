CREATE DATABASE bank_system;
USE bank_system;

CREATE TABLE branch (
    BranchId VARCHAR(40) PRIMARY KEY,
    BranchName VARCHAR(50) NOT NULL,
    PostalAddress TEXT NOT NULL
    );

CREATE TABLE employee (
    EmployeeId VARCHAR(40) PRIMARY KEY,
    FirstName varchar(50) NOT NULL,
    LastName varchar(50) NOT NULL,
    BranchId VARCHAR(40),
    Nic CHAR(10) NOT NULL,
    Email VARCHAR(250) NOT NULL,
    BirthDate DATE NOT NULL,
    FOREIGN KEY (BranchId) REFERENCES branch (BranchId)
    );

CREATE TABLE employee_login (
    EmployeeId VARCHAR(40) NOT NULL,
    UserName VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(500) NOT NULL,
    Status ENUM('1','0') NOT NULL,
    Type ENUM('Normal','BranchManager'),
    FOREIGN KEY (EmployeeId) REFERENCES employee(EmployeeId)
    );

CREATE TABLE branch_manager (
     ManagerId VARCHAR(40) PRIMARY KEY,
     EmployeeId VARCHAR(40) ,
     BranchId VARCHAR(40) ,
     FOREIGN KEY (EmployeeId) REFERENCES employee(EmployeeId),
     FOREIGN KEY (BranchId) REFERENCES branch(BranchId)
     );

CREATE TABLE branch_managers_history (
    ManagerId VARCHAR(40),
    BranchId VARCHAR(40),
    JoinedDate DATE NOT NULL,
    LeftDate DATE,
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
    LeftDate DATE,
    PRIMARY KEY (EmployeeId, BranchId, JoinedDate),
    FOREIGN KEY (EmployeeId) REFERENCES normal_employee(EmployeeId),
    FOREIGN KEY (BranchId) REFERENCES branch(BranchId)
    );

CREATE TABLE customer (
    CustomerId VARCHAR(40) PRIMARY KEY,
    CustomerType ENUM('Individual','Organization') NOT NULL,
    Email VARCHAR(250) UNIQUE
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
    PostalCode CHAR(5) NOT NULL,
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
    PostalCode CHAR(5) NOT NULL,
    TelephoneNumber CHAR(10) NOT NULL,
    AuthorizedPersonNic CHAR(10) NOT NULL,
    FOREIGN KEY (CustomerId) REFERENCES customer(CustomerId)
    );

CREATE TABLE customer_login (
    CustomerId VARCHAR(40) PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(256) NOT NULL,
    Status ENUM('0','1') NOT NULL,
    FOREIGN KEY (CustomerId) REFERENCES customer(CustomerId)
    );

CREATE TABLE savings_accounts_type (
    AccountTypeId VARCHAR(20) PRIMARY KEY,
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
    Balance DECIMAL(12,2) NOT NULL,
    Status ENUM('0','1') NOT NULL,
    FOREIGN KEY (BranchId) REFERENCES branch(BranchId),
    FOREIGN KEY (AccountTypeId) REFERENCES savings_accounts_type(AccountTypeId)
    );

CREATE TABLE savings_customers(
    AccountNumber VARCHAR(40),
    CustomerId VARCHAR(40),
    PRIMARY KEY(AccountNumber, CustomerId),
    FOREIGN KEY(CustomerId) REFERENCES customer(CustomerId),
    FOREIGN KEY(AccountNumber) REFERENCES savings_account(AccountNumber)
    );

CREATE TABLE savings_transaction (
    TransactionId VARCHAR(40) PRIMARY KEY,
    AccountNumber VARCHAR(40) NOT NULL,
    TransactionDate DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    TransactionType ENUM('Deposit','Withdraw') NOT NULL,
    Teller ENUM('ATM','OnlineTransfer','EMP','SYSTEM') NOT NULL,
    FOREIGN KEY(AccountNumber) REFERENCES savings_account(AccountNumber)
    );

CREATE TABLE employee_savings_transaction(
    EmployeeId VARCHAR(40) NOT NULL,
    TransactionId VARCHAR(40) PRIMARY KEY,
    FOREIGN KEY(TransactionId) REFERENCES savings_transaction(TransactionId),
    FOREIGN KEY(EmployeeId) REFERENCES employee(EmployeeId)
    );

CREATE TABLE fixed_deposit_type(
    FDType VARCHAR(10) PRIMARY KEY,
    Duration INT(4) NOT NULL,
    InterestRate DECIMAL(5,2) NOT NULL
    );

CREATE TABLE fixed_deposit_account (
    FDAccountNumber VARCHAR(40) PRIMARY KEY,
    SavingsAccountNumber VARCHAR(40) NOT NULL,
    CustomerId VARCHAR(40) NOT NULL,
    DepositAmount DECIMAL(12,2) NOT NULL,
    DepositDate DATE NOT NULL,
    MaturityDate DATE NOT NULL,
    FDType VARCHAR(10),
    FOREIGN KEY(FDType) REFERENCES fixed_deposit_type(FDType),
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

CREATE TABLE current_transaction(
    TransactionId VARCHAR(40) PRIMARY KEY,
    EmployeeId VARCHAR(40) NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    TransactionDate DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    ChequeNumber VARCHAR(40) NOT NULL,
    FOREIGN KEY(EmployeeId) REFERENCES employee(EmployeeId),
    FOREIGN KEY(AccountNumber) REFERENCES current_account(AccountNumber)
    );

CREATE TABLE current_account_deposit (
    DepositId VARCHAR(40) PRIMARY KEY,
    TransactionId VARCHAR(40) NOT NULL,
    FOREIGN KEY(TransactionId) REFERENCES current_transaction(TransactionId)
    );

CREATE TABLE current_account_withdraw (
    WithdrawId VARCHAR(40) PRIMARY KEY,
    TransactionId VARCHAR(40) NOT NULL,
    FOREIGN KEY(TransactionId) REFERENCES current_transaction(TransactionId)
    );

CREATE TABLE transfer (
    TransferId VARCHAR(40) PRIMARY KEY,
    TransferredFromAccountNumber VARCHAR(40) NOT NULL,
    TransferredToAccountNumber VARCHAR(40) NOT NULL,
   	TransferAmount DECIMAL(10,2) NOT NULL,
    TransferDate DATE NOT NULL,
    WithdrawId VARCHAR(40) NOT NULL,
    DepositId VARCHAR(40) NOT NULL,
    FOREIGN KEY(WithdrawId) REFERENCES savings_transaction(TransactionId),
    FOREIGN KEY(DepositId) REFERENCES savings_transaction(TransactionId),
    FOREIGN KEY(TransferredFromAccountNumber) REFERENCES savings_account(AccountNumber),
    FOREIGN KEY(TransferredToAccountNumber) REFERENCES savings_account(AccountNumber)
    );

CREATE TABLE debit_card (
    CardNumber CHAR(16) PRIMARY KEY,
    PinNumber CHAR(4) NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    IssuedDate DATE NOT NULL,
    ExpiryDate DATE NOT NULL,
    FOREIGN KEY (AccountNumber) REFERENCES savings_account(AccountNumber)
    );

CREATE TABLE atm_withdraw (
    WithdrawId VARCHAR(40) PRIMARY KEY,
    CardNumber VARCHAR(16) NOT NULL,
    WithdrawDate DATETIME NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY(WithdrawId) REFERENCES savings_withdraw(WithdrawId),
    FOREIGN KEY(CardNumber) REFERENCES debit_card(CardNumber)
    );

CREATE TABLE loan_type(
    LoanTypeId VARCHAR(10) PRIMARY KEY,
    LoanTypeName VARCHAR(10) NOT NULL,
    InterestRate DECIMAL(4,2) NOT NULL
    );

CREATE TABLE loan(
    LoanId VARCHAR(40) PRIMARY KEY,
    LoanTypeId VARCHAR(10) NOT NULL,
    Amount DECIMAL(8,2) NOT NULL,
    SettlementPeriod int(3) NOT NULL,
    NoOfSettlements int(3) NOT NULL,
    PaidSettlements DECIMAL(8,2) NOT NULL ,
    LoanStatus ENUM('FullyPaid','Current','Late','Cancelled') NOT NULL,
    InstallmentType ENUM('MONTHLY','PERIODICAL') NOT NULL,
    FOREIGN KEY(LoanTypeId) REFERENCES loan_type(LoanTypeId)
    );

CREATE TABLE online_loan(
    LoanId VARCHAR(40),
    Amount DECIMAL(7,2) NOT NULL,
    DepositId VARCHAR(40) PRIMARY KEY,
    CustomerId VARCHAR(40)  NOT NULL,
    FOREIGN KEY(CustomerId) REFERENCES customer(CustomerId),
    FOREIGN KEY(LoanId) REFERENCES loan(LoanId)
    );

CREATE TABLE loan_installment_history(
    InstallmentId VARCHAR(40) PRIMARY KEY,
    LoanId VARCHAR(40),
    InstallmentDate DATE ,
    Amount DECIMAL(7,2) ,
    DueDate DATE NOT NULL,
    LateCharges DECIMAL(7,2) ,
    FOREIGN KEY(LoanId) REFERENCES loan(LoanId)
    );

CREATE TABLE loan_request(
    RequestId VARCHAR(40) PRIMARY KEY,
    CustomerId VARCHAR(40) NOT NULL,
    ManagerId VARCHAR(40) NOT NULL,
    Amount DECIMAL(8,2) NOT NULL,
    ApprovedStatus ENUM('APPROVED','PENDING','REJECTED') DEFAULT 'PENDING',
    EmployeeId VARCHAR(40) NOT NULL,
    GrossSalary DECIMAL(10,2) NOT NULL,
    NetSalary DECIMAL(10,2) NOT NULL,
    LoanId VARCHAR(40) NULL,
    EmploymentSector ENUM('PRIVATE',"GOV","SELF") NOT NULL,
    EmploymentType ENUM('PER',"TEMP") NULL,
    Profession TEXT NOT NULL,
    FOREIGN KEY(CustomerId) REFERENCES customer(CustomerId),
    FOREIGN KEY(ManagerId) REFERENCES branch_manager(ManagerId),
    FOREIGN KEY(EmployeeId) REFERENCES employee(EmployeeId),
    FOREIGN KEy(LoanId) REFERENCES loan(LoanId)
    );

