CREATE DATABASE banksystem;
USE banksystem;

CREATE TABLE Employee (
    EmployeeId VARCHAR(40) ,
    FirstName varchar(50) NOT NULL,
    LastName varchar(50) NOT NULL,
    BranchID VARCHAR(40),
    Nic CHAR(10) NOT NULL,
    BirthDate DATE NOT NULL,
    PRIMARY KEY(EmployeeId)
    /*FOREIGN KEY (BranchID) REFERENCES Branch (BranchID)*/
    );

CREATE TABLE EmployeeLogin (
    EmployeeID VARCHAR(40) NOT NULL,
    Username VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(50) NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeId)
    );

CREATE TABLE BranchManager (
     ManagerId VARCHAR(40) PRIMARY KEY,
     EmployeeId VARCHAR(40) NOT NULL,
     BranchID VARCHAR(40),
     FOREIGN KEY (EmployeeId) REFERENCES Employee(EmployeeId)
 	/*FOREIGN KEY (BranchId) REFERENCES Branch(BranchId)*/
     );

CREATE TABLE Branch (
    BranchId VARCHAR(40) PRIMARY KEY,
    BranchName VARCHAR(50) NOT NULL,
    ManagerId VARCHAR(40),
    PostalAddress TEXT NOT NULL,
    FOREIGN KEY (ManagerId) REFERENCES branchmanager(ManagerId)
    );

CREATE TABLE BranchManagersHistory (
    ManagerId VARCHAR(40),
    BranchId VARCHAR(40),
    JoinedDate DATE,
    LeaveDate DATE,
    PRIMARY KEY (ManagerId, BranchId, JoinedDate),
    FOREIGN KEY (ManagerId) REFERENCES BranchManager(ManagerId),
    FOREIGN KEY (BranchID) REFERENCES Branch(BranchID)
    );

CREATE TABLE NormalEmployee (
    EmployeeId VARCHAR(40) PRIMARY KEY,
    BranchId VARCHAR(40),
    FOREIGN KEY (EmployeeId) REFERENCES Employee(EmployeeId),
    FOREIGN KEY (BranchId) REFERENCES Branch(BranchId)
    );

CREATE TABLE NormalEmployeesHistory (
    EmployeeId VARCHAR(40),
    BranchId VARCHAR(40),
    JoinedDate DATE,
    LeaveDate DATE,
    PRIMARY KEY (EmployeeId, BranchId, JoinedDate),
    FOREIGN KEY (EmployeeId) REFERENCES normalemployee(EmployeeId),
    FOREIGN KEY (BranchID) REFERENCES Branch(BranchID)
    );

CREATE TABLE Customer (
    CustomerId VARCHAR(40) PRIMARY KEY,
    CustomerType ENUM('Individual','Organization') NOT NULL,
    Email VARCHAR(50)
    );

CREATE TABLE Individual (
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
    FOREIGN KEY (CustomerId) REFERENCES Customer(CustomerId)
    );

CREATE TABLE Organization (
    CustomerId VARCHAR(40) PRIMARY KEY,
    OrganizationName VARCHAR(50) NOT NULL,
    AuthorizedPerson VARCHAR(50) NOT NULL,
    RegisterNumber VARCHAR(40) NOT NULL,
    BuildingNumber VARCHAR(40) NOT NULL,
    StreetOne VARCHAR(50) NOT NULL,
    StreetTwo VARCHAR(50),
    Town VARCHAR(50) NOT NULL,
    PostalCode VARCHAR(50) NOT NULL,
    TelephoneNumber INT(10) NOT NULL,
    FOREIGN KEY (CustomerId) REFERENCES Customer(CustomerId)
    );

CREATE TABLE CustomerOnlineAccount (
    AccountId VARCHAR(40) PRIMARY KEY,
    CustomerId VARCHAR(40) UNIQUE NOT NULL,
    Email VARCHAR(50) NOT NULL,
    FOREIGN KEY (CustomerId) REFERENCES Customer(CustomerId)
    );

CREATE TABLE CustomerLogin (
    AccountId VARCHAR(40) NOT NULL,
    Username VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(40) NOT NULL,
    FOREIGN KEY (AccountId) REFERENCES customeronlineaccount(AccountId)
    );

CREATE TABLE SavingsAccountsType (
    AccountTypeId VARCHAR(40) PRIMARY KEY,
    AccountTypeName VARCHAR(50) NOT NULL,
    InterestRate DECIMAL(10,2) NOT NULL,
    MinimumAge INT NOT NULL,
    MaximumAge INT NOT NULL,
    MinimumBalance DECIMAL(10,2)
    );

CREATE TABLE SavingsAccount (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    BranchId VARCHAR(40) NOT NULL,
    AccountTypeId VARCHAR(40) NOT NULL,
    CustomerId VARCHAR(40) NOT NULL,
    FOREIGN KEY (BranchId) REFERENCES Branch(BranchId),
    FOREIGN KEY (AccountTypeId) REFERENCES SavingsAccountsType(AccountTypeId),
    FOREIGN KEY (CustomerId) REFERENCES customer(CustomerId)
    );

CREATE TABLE DebitCard (
    CardNumber VARCHAR(40) PRIMARY KEY,
    CardCode VARCHAR(40) UNIQUE NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    IssuedDate DATE NOT NULL,
    ExpiryDate DATE NOT NULL,
    FOREIGN KEY (AccountNumber) REFERENCES savingsaccount(AccountNumber)
    );

CREATE TABLE SavingsWithdraw (
    WithdrawId VARCHAR(40) PRIMARY KEY,
    EmployeeId VARCHAR(40) NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    WithdrawDate DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    Teller VARCHAR(50) NOT NULL,
    FOREIGN KEY(EmployeeId) REFERENCES Employee(EmployeeId),
    FOREIGN KEY(AccountNumber) REFERENCES SavingsAccount(AccountNumber)
    );

CREATE TABLE SavingsDeposit (
    DepositId VARCHAR(40) PRIMARY KEY,
    EmployeeId VARCHAR(40) NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    DepositDate DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    Teller VARCHAR(50) NOT NULL,
    FOREIGN KEY(EmployeeId) REFERENCES Employee(EmployeeId),
    FOREIGN KEY(AccountNumber) REFERENCES SavingsAccount(AccountNumber)
    );

CREATE TABLE FixedDepositAccount (
    FDAccountNumber VARCHAR(40) PRIMARY KEY,
    SavingsAccountNumber VARCHAR(40) NOT NULL,
    CustomerId VARCHAR(40) NOT NULL,
    InterestRate DECIMAL(10,2) NOT NULL,
    DepositAmount DECIMAL(10,2) NOT NULL,
    DepositDate DATE NOT NULL,
    MaturityDate DATE NOT NULL,
    FOREIGN KEY(SavingsAccountNumber) REFERENCES SavingsAccount(AccountNumber),
    FOREIGN KEY(CustomerId) REFERENCES Customer(CustomerId)
    );

CREATE TABLE CurrentAccount (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    BranchId VARCHAR(40) NOT NULL,
    CustomerId VARCHAR(40) NOT NULL,
    FOREIGN KEY (BranchId) REFERENCES Branch(BranchId),
    FOREIGN KEY (CustomerId) REFERENCES Customer(CustomerId)
    );

CREATE TABLE CurrentAccountOpen (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    OpenEmployeeId VARCHAR(40) NOT NULL,
    OpenDate DATE NOT NULL,
    FOREIGN KEY (AccountNumber) REFERENCES CurrentAccount(AccountNumber),
    FOREIGN KEY (OpenEmployeeId) REFERENCES Employee(EmployeeId)
    );

CREATE TABLE CurrentAccountClose (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    CloseEmployeeId VARCHAR(40) NOT NULL,
    CloseDate DATE NOT NULL,
    FOREIGN KEY (AccountNumber) REFERENCES CurrentAccount(AccountNumber),
    FOREIGN KEY (CloseEmployeeId) REFERENCES Employee(EmployeeId)
    );

CREATE TABLE SavingsOpen (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    OpenEmployeeId VARCHAR(40) NOT NULL,
    OpenDate DATE NOT NULL,
    FOREIGN KEY (AccountNumber) REFERENCES SavingsAccount(AccountNumber),
    FOREIGN KEY (OpenEmployeeId) REFERENCES Employee(EmployeeId)
    );

CREATE TABLE SavingsClose (
    AccountNumber VARCHAR(40) PRIMARY KEY,
    CloseEmployeeId VARCHAR(40) NOT NULL,
    CloseDate DATE NOT NULL,
    FOREIGN KEY (AccountNumber) REFERENCES SavingsAccount(AccountNumber),
    FOREIGN KEY (CloseEmployeeId) REFERENCES Employee(EmployeeId)
    );

CREATE TABLE CurrentAccountDeposit (
    DepositId VARCHAR(40) PRIMARY KEY,
    EmployeeId VARCHAR(40) NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    DepositDate DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    ChequeNumber VARCHAR(40) NOT NULL,
    FOREIGN KEY(EmployeeId) REFERENCES Employee(EmployeeId),
    FOREIGN KEY(AccountNumber) REFERENCES CurrentAccount(AccountNumber)
    );

CREATE TABLE CurrentAccountWithdraw (
    WithdrawId VARCHAR(40) PRIMARY KEY,
    EmployeeId VARCHAR(40) NOT NULL,
    AccountNumber VARCHAR(40) NOT NULL,
    WithdrawDate DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    ChequeNumber VARCHAR(40) NOT NULL,
    FOREIGN KEY(EmployeeId) REFERENCES Employee(EmployeeId),
    FOREIGN KEY(AccountNumber) REFERENCES CurrentAccount(AccountNumber)
    );

CREATE TABLE Transfer (
    TransferId VARCHAR(40) PRIMARY KEY,
    TransferredFromAccountNumber VARCHAR(40) NOT NULL,
    TransferredToAccountNumber VARCHAR(40) NOT NULL,
   	TransferAmount DECIMAL(10,2) NOT NULL,
    Date DATE NOT NULL,
    FOREIGN KEY(TransferredFromAccountNumber) REFERENCES SavingsAccount(AccountNumber),
    FOREIGN KEY(TransferredToAccountNumber) REFERENCES SavingsAccount(AccountNumber)
    );

CREATE TABLE ATMWithdraw (
    WithdrawId VARCHAR(40) PRIMARY KEY,
    CardNumber VARCHAR(40) NOT NULL,
    WithdrawDate DATETIME NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY(CardNumber) REFERENCES DebitCard(CardNumber)
    );