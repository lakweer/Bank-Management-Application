CREATE database bank;

use bank;

CREATE TABLE branch(
    BranchID varchar(20) PRIMARY KEY,
    BranchName varchar(20) NOT NULL,
    Address BLOB NOT NULL
    );

CREATE TABLE employee(
    EmployeeID varchar(40) PRIMARY KEY,
    FirstName varchar(20) NOT NULL,
    LastName varchar(20) NOT NULL,
    Nic char(10) NOT NULL UNIQUE,
    Email varchar(250) NOT NULL UNIQUE,
    BirthDate Date NOT NULL
    );

CREATE TABLE employeeBranch(
    BranchID varchar(20),
    EmployeeID varchar(40),
    JoinedDate Date NOT NULL,
    Leaved Date,
    PRIMARY KEY(BranchID, EmployeeID, JoinedDate),
    FOREIGN KEY(BranchID) REFERENCES branch(BranchID),
    FOREIGN KEY(EmployeeID) REFERENCES employee(EmployeeID)
    );

CREATE TABLE users(
    EmployeeID varchar(40) PRIMARY KEY,
    UserName varchar(40) NOT NULL UNIQUE,
    Password varchar(512) NOT NULL,
    Status ENUM('1','0') NOT NULL
    );

INSERT INTO `branch`(`BranchID`, `BranchName`, `Address`) VALUES ('BOSL_001_COL_NUG','NUGEGODA','No.56, Bank Of Sri Lanka, High Level Road, Nugegoda');

INSERT INTO `employee`(`EmployeeID`, `FirstName`, `LastName`, `Nic`, `Email`, `BirthDate`) VALUES
(uuid(),'Alan','Walker','123456789V','alanwalker@gmail.com','1992-01-10');

INSERT INTO `users`(`EmployeeID`, `UserName`, `Password`, `Status`) VALUES
('5acbfcf0-12b1-11ea-8718-d8c4971f41ea','admin','admin','1');

INSERT INTO `employeebranch`(`BranchID`, `EmployeeID`, `JoinedDate`, `Leaved`) VALUES
('BOSL_001_COL_NUG','5acbfcf0-12b1-11ea-8718-d8c4971f41ea','2019-11-29',NULL);