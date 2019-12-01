--Branch Insert
INSERT INTO `branch`(`BranchId`, `BranchName`, `PostalAddress`) VALUES
('BOSL_001_COL_NUG','NUGEGODA','No.56, Bank Of Sri Lanka, High Level Road, Nugegoda');

--Employee Insert
INSERT INTO `employee`(`EmployeeId`, `FirstName`, `LastName`, `BranchId`, `Nic`, `Email`, `BirthDate`) VALUES
(uuid(),'Alan','Walker','BOSL_001_COL_NUG','123456789V','alanwalker@gmail.com','1992-01-10');

--Employee Login
INSERT INTO `employee_login`(`EmployeeId`, `UserName`, `Password`, `Status`, `Type`) VALUES
('5a4c34ed-13fa-11ea-8718-d8c4971f41ea','admin','admin','1','Normal');

--normal_employee
INSERT INTO `normal_employee`(`EmployeeId`) VALUES
('5a4c34ed-13fa-11ea-8718-d8c4971f41ea');

--normal_employees_history
INSERT INTO `normal_employees_history`(`EmployeeId`, `BranchId`, `JoinedDate`, `LeaveDate`) VALUES
('5a4c34ed-13fa-11ea-8718-d8c4971f41ea','BOSL_001_COL_NUG','2019-11-29',NULL);