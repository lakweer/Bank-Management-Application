/* branch insert statements */
INSERT INTO `branch` (`BranchId`, `BranchName`, `PostalAddress`) VALUES
('BOSL_001_COL_NUG', 'NUGEGODA', 'No.56, Bank Of Sri Lanka, High Level Road, Nugegoda'),
('BOSL_002_COL_MAH', 'Maharagama', 'No.10, High Level Road, Maharagama'),
('BOSL_003_GMP_GMP', 'Gampaha', 'No.10, Gampaha Road, Gampaha'),
('BOSL_004_COL_CEN', 'MAIN', 'No.20, Walukarama Road, Colomo 07'),
('BOSL_005_COL_BOR', 'Borella', 'No.10, Kota Road, Borella');


/* Savings Account Types */
INSERT INTO `savings_accounts_type`(`AccountTypeId`, `AccountTypeName`, `InterestRate`, `MinimumAge`, `MaximumAge`, `MinimumBalance`) VALUES
('SA_TYPE_001','Children',0.12,0,18,0.00),
('SA_TYPE_002','Teen',0.11,13,19,500.00),
('SA_TYPE_003','18+',0.10,18,59,1000.00),
('SA_TYPE_004','Senior Citizen',0.13,60,100,1000.0);


/* FD Account Types */
INSERT INTO `fixed_deposit_type`(`FDType`, `Duration`, `InterestRate`)
VALUES ("Type_1", 6, 0.13),
("Type_2", 12, 0.14),
("Type_3", 36, 0.15);


/*loan Types*/
INSERT INTO `loan_type` (`LoanTypeId`, `LoanTypeName`, `InterestRate`) VALUES
('1', 'Educational', '12.50'),
('2', 'Housing', '12.50'),
('3', 'Personal', '14.50'),
('4', 'Vehicle', '17.50');


/*employee*/
INSERT INTO `employee` (`EmployeeId`, `FirstName`, `LastName`, `BranchId`, `Nic`, `Email`, `BirthDate`) VALUES
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'Alan', 'Walker', 'BOSL_001_COL_NUG', '123456789V', 'alanwalker@gmail.com', '1992-01-10'),
('c44cb195-1e1f-11ea-b1ca-d8c4971f41ea', 'Brnach', 'Manager', 'BOSL_001_COL_NUG', '789456123V', 'branchmanager@gmail.com', '1992-01-10');


/*employee login*/
INSERT INTO `employee_login` (`EmployeeId`, `UserName`, `Password`, `Status`, `Type`) VALUES
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'admin', '9ca694a90285c034432c9550421b7b9dbd5c0f4b6673f05f6dbce58052ba20e4248041956ee8c9a2ec9f10290cdc0782', '1', 'Normal'),
('c44cb195-1e1f-11ea-b1ca-d8c4971f41ea', 'manager', '300f04de8446334e084d7cd0a728c1bd46f218eae5aca0989a3b31835e4cf39a7596a0f751fcfea11bfd3109a3ead62', '1', 'BranchManager');


/*branch manager*/
INSERT INTO `branch_manager` (`ManagerId`, `EmployeeId`, `BranchId`) VALUES
('178af19e-1e20-11ea-b1ca-d8c4971f41ea', 'c44cb195-1e1f-11ea-b1ca-d8c4971f41ea', 'BOSL_001_COL_NUG');


/*branch manager history*/
INSERT INTO `branch_managers_history` (`ManagerId`, `BranchId`, `JoinedDate`, `LeftDate`) VALUES
('178af19e-1e20-11ea-b1ca-d8c4971f41ea', 'BOSL_001_COL_NUG', '2019-12-14', NULL);


/*normal employee*/
INSERT INTO `normal_employee` (`EmployeeId`) VALUES ('23adad46-1bb9-11ea-a1fc-d8c4971f41ea');


/*normal employee history*/
INSERT INTO `normal_employees_history` (`EmployeeId`, `BranchId`, `JoinedDate`, `LeftDate`) VALUES
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'BOSL_001_COL_NUG', '2019-11-29', NULL);



/*customer*/
INSERT INTO `customer` (`CustomerId`, `CustomerType`, `Email`) VALUES
('42c07756-20a3-11ea-b1ca-d8c4971f41ea', 'Child', NULL),
('ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', 'Individual', 'lak@gmail.com'),
('b72a8c6f-2553-11ea-8d42-c8d3ff1d6347', 'Organization', 'hash@gmail.com'),
('1efeb027-2b3a-11ea-8d4e-30e37ab25bb4', 'Individual', 'nilmanikulaweera@gmail.com');


/*individual*/
INSERT INTO `individual` (`CustomerId`, `Nic`, `FirstName`, `LastName`, `HouseNumber`, `StreetOne`, `StreetTwo`, `Town`, `District`, `PostalCode`, `Gender`, `Birthday`) VALUES
('1efeb027-2b3a-11ea-8d4e-30e37ab25bb4', '985030774V', 'Nilmani ', 'Kulaweera', '16A', 'Dharmarathna Avenue', 'Rawathawatte', 'Moratuwa', 'Colombo', '11111', 'Female', '1998-01-03');
INSERT INTO `individual` (`CustomerId`, `Nic`, `FirstName`, `LastName`, `HouseNumber`, `StreetOne`, `StreetTwo`, `Town`, `District`, `PostalCode`, `Gender`, `Birthday`) VALUES
('ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '980083837V', 'Laskshan ', 'Weerasinghe', '12', 'st', 'sas', 'sadd', 'xsccac', '11111', 'Male', '1999-01-08');


/*children*/
INSERT INTO `children` (`CustomerId`, `FullName`, `BirthDate`, `GuardianId`) VALUES
('42c07756-20a3-11ea-b1ca-d8c4971f41ea', 'Danapal Daj', '2012-12-13', 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea');


/*organization*/
INSERT INTO `organization` (`CustomerId`, `OrganizationName`, `AuthorizedPerson`, `RegisterNumber`, `BuildingNumber`, `StreetOne`, `StreetTwo`, `Town`, `PostalCode`, `TelephoneNumber`, `AuthorizedPersonNic`) VALUES
('b72a8c6f-2553-11ea-8d42-c8d3ff1d6347', 'hello', 'Hashara', '123456O', '5', 'dd', 'dd', 'ee', '55555', '0112454545', '123456789V');


/*savings account*/
INSERT INTO `savings_account` (`AccountNumber`, `BranchId`, `AccountTypeId`, `Balance`, `Status`) VALUES
('ABC123', 'BOSL_001_COL_NUG', 'SA_TYPE_003', '1000.00', '1');

/*fixed deposits*/
INSERT INTO `fixed_deposit_account` (`FDAccountNumber`, `SavingsAccountNumber`, `CustomerId`, `DepositAmount`, `DepositDate`, `MaturityDate`, `FDType`) VALUES
('FD0001', 'ABC123', 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '10000.00', '2019-12-13', '2020-06-10', 'Type_1');


/*loan request*/
INSERT INTO `loan_request` (`RequestId`, `CustomerId`, `BranchId`, `Amount`, `ApprovedStatus`, `EmployeeId`, `GrossSalary`, `NetSalary`, `LoanId`, `EmploymentSector`, `EmploymentType`, `Profession`, `LoanTypeId`, `requestDate`) VALUES
(1, 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', 'BOSL_001_COL_NUG', '1555.00', 'PENDING', '23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '122.00', '158.00', NULL, 'GOV', 'PER', 'Bajnji', '1', '2019-12-22');


