/* branch insert statements */
INSERT INTO `branch`(`BranchId`, `BranchName`, `PostalAddress`) VALUES
('BOSL_001_COL_NUG','NUGEGODA','No.56, Bank Of Sri Lanka, High Level Road, Nugegoda');
INSERT INTO `branch`(`BranchId`, `BranchName`, `PostalAddress`)
VALUES ("BOSL_002_COL_MAH", "Maharagama","No.10, High Level Road, Maharagama"),
("BOSL_003_GMP_GMP", "Gampaha","No.10, Gampaha Road, Gampaha"),
("BOSL_004_COL_CEN", "MAIN","No.20, Walukarama Road, Colomo 07"),
("BOSL_005_COL_BOR", "Borella","No.10, Kota Road, Borella");

/* Savings Account Types */
INSERT INTO `savings_accounts_type`(`AccountTypeId`, `AccountTypeName`, `InterestRate`, `MinimumAge`, `MaximumAge`, `MinimumBalance`) VALUES
('SA_TYPE_001','Children',0.12,0,18,0.00), ('SA_TYPE_002','Teen',0.11,13,19,500.00), ('SA_TYPE_003','18+',0.10,18,59,1000.00), ('SA_TYPE_004','Senior Citizen',0.13,60,100,1000.0);

/* FD Account Types */
INSERT INTO `fixed_deposit_type`(`FDType`, `Duration`, `InterestRate`)
VALUES ("Type_1", 6, 0.13), ("Type_2", 12, 0.14), ("Type_3", 36, 0.15) ;




