--Branch Insert
INSERT INTO `branch`(`BranchId`, `BranchName`, `PostalAddress`) VALUES
('BOSL_001_COL_NUG','NUGEGODA','No.56, Bank Of Sri Lanka, High Level Road, Nugegoda');

--Employee Insert
INSERT INTO `employee`(`EmployeeId`, `FirstName`, `LastName`, `BranchId`, `Nic`, `Email`, `BirthDate`) VALUES
(uuid(),'Alan','Walker','BOSL_001_COL_NUG','123456789V','alanwalker@gmail.com','1992-01-10');

--Employee Login
INSERT INTO `employee_login`(`EmployeeId`, `UserName`, `Password`, `Status`, `Type`) VALUES
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea','admin','admin','1','Normal');

--normal_employee
INSERT INTO `normal_employee`(`EmployeeId`) VALUES
('5a4c34ed-13fa-11ea-8718-d8c4971f41ea');

--normal_employees_history
INSERT INTO `normal_employees_history`(`EmployeeId`, `BranchId`, `JoinedDate`, `LeaveDate`) VALUES
('5a4c34ed-13fa-11ea-8718-d8c4971f41ea','BOSL_001_COL_NUG','2019-11-29',NULL);

--Savings Account Types
INSERT INTO `savings_accounts_type`(`AccountTypeId`, `AccountTypeName`, `InterestRate`, `MinimumAge`, `MaximumAge`, `MinimumBalance`) VALUES
('SA_TYPE_001','Children',0.12,0,18,0.00), ('SA_TYPE_002','Teen',0.11,13,19,500.00), ('SA_TYPE_003','18+',0.10,18,59,1000.00), ('SA_TYPE_004','Senior Citizen',0.13,60,100,1000.0);

DECLARE rowcount INT;

SELECT COUNT(*) INTO rowcount FROM savings_transaction WHERE
AccountNumber = NEW.AccountNumber AND TransactionType= NEW.TransactionType
AND MONTH(savings_transaction.TransactionDate) = MONTH(CURDATE()) ;

IF rowcount >= 5 THEN
    ELSE
        INSERT INTO `savings_transaction`(`TransactionId`, `AccountNumber`, `TransactionDate`, 				`Amount`, `Teller`, `TransactionType`) VALUES
        (NEW.TransactionId, NEW.AccountNumber,NEW.TransactionDate,
         NEW.Amount ,NEW.Teller , NEW.TransactionType );
    END IF;





