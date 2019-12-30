-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 30, 2019 at 02:49 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank_system`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addExistingEmployeeToBranch` (IN `nicArg` CHAR(10), IN `branchIdArg` VARCHAR(40))  MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE empIdArg VARCHAR(40);
DECLARE returnArg VARCHAR(40);

/* BEGIN TRANSACTION */
START TRANSACTION;

SET returnArg = "Some thing went wrong during the process";

/* selcect the employee id from employee table */
SELECT `EmployeeId` INTO empIdArg FROM `employee` WHERE `Nic` = nicArg;

IF LENGTH(empIdArg) > 0 THEN

	/* update the employee table */	
    UPDATE `employee` SET `BranchId`= branchIdArg  WHERE `EmployeeId` = empIdArg;
    
    /* update the employee login table */
    UPDATE `employee_login` SET `Status`= "1" ,`Type`= "Normal" WHERE `EmployeeId` = empIdArg;
    
    /* insert the values in to normal employee history table */
    INSERT INTO `normal_employees_history`(`EmployeeId`, `BranchId`, `JoinedDate`) VALUES (empIdArg, branchIdArg, CURDATE());
    
    SET returnArg = "Success";
    COMMIT;
    
ELSE
	SET returnArg = "Employee Doesn't Exist";
END IF;

SELECT returnArg;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewEmployee` (IN `firstNameArg` VARCHAR(50), IN `lastNameArg` VARCHAR(50), IN `branchIdArg` VARCHAR(40), IN `nicArg` CHAR(10), IN `emailArg` VARCHAR(250), IN `dobArg` DATE, IN `empTypeArg` ENUM("Normal","BranchManager"), IN `usernameArg` VARCHAR(50), IN `passwordArg` VARCHAR(500))  MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE userNameExistArg INT(2);
DECLARE branchManagerIDArg VARCHAR(40);
DECLARE empIdArg VARCHAR(40);
DECLARE nicExistArg INT(2);
DECLARE returnArg VARCHAR(100);
    
/* BEGIN TRANSACTION */
START TRANSACTION;

/* Set Default Statement */
SET returnArg = "Something Went Wrong Adding the Employee!";

    /* check the employee is already registred */
    SELECT COUNT(*) INTO nicExistArg FROM `employee` WHERE `Nic`= nicArg;
    
    IF nicExistArg = 0 THEN
    
    	/* select a new employee id */
    	SELECT UUID() INTO empIdArg;

        /* Add the Employee to the Employee Table */
        INSERT INTO `employee`(`EmployeeId`, `FirstName`, `LastName`, `BranchId`, `Nic`, `Email`, `BirthDate`) 
    	VALUES (empIdArg, firstNameArg, lastNameArg, branchIdArg, nicArg, emailArg, dobArg);
    
    	/* check if the user name is already used */
    	SELECT COUNT(*) INTO userNameExistArg FROM `employee_login` WHERE `UserName` = usernameArg;
    
    	IF userNameExistArg = 0 THEN
    
    		/* add the employee to login table */
    		INSERT INTO `employee_login`(`EmployeeId`, `UserName`, `Password`, `Status`, `Type`) VALUES 
    		(empIdArg, usernameArg, passwordArg, "1", empTypeArg);
    
            /*check for the normal employee type */
            IF empTypeArg = "Normal" THEN

                /* add the employee to normal employee table */
                INSERT INTO `normal_employee`(`EmployeeId`) VALUES (empIdArg);

                /* add the employee to normal employee history table */
                INSERT INTO `normal_employees_history`(`EmployeeId`, `BranchId`, `JoinedDate`) 
                VALUES (empIdArg, branchIdArg, CURDATE());
    
    			SET returnArg = "Success";
    			COMMIT;
    		
    		END IF;
    
    		/*check for the branch manager type */
			IF empTypeArg = "BranchManager" THEN
    			
    			/* select a branch manager id */
    			SELECT UUID() INTO branchManagerIDArg;
    
    			/* insert the values into mager table */
    			INSERT INTO `branch_manager`(`ManagerId`, `EmployeeId`, `BranchId`) VALUES (branchManagerIDArg, empIdArg, branchIdArg);
    
    			/* insert the values into branch manager history table */
    			INSERT INTO `branch_managers_history`(`ManagerId`, `BranchId`, `JoinedDate`) VALUES (branchManagerIDArg, branchIdArg, CURDATE());
    
   				SET returnArg = "Success";
    			COMMIT;
    		
    		END IF;
    
    	ELSE
    		SET returnArg = "User Name is already used";
    	END IF;
    ELSE 
    	SET returnArg = "Employee is already Registered!";
    END IF;

    SELECT returnArg;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addOnlineCustomer` (IN `nid` VARCHAR(50))  MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE nicExistArg INT(2);
DECLARE nicExistInOnlineAccount INT(2);
DECLARE getCustomerID VARCHAR(40);
DECLARE returnArg VARCHAR(100);

/* BEGIN TRANSACTION */
START TRANSACTION;

/* Set Default Statement */
SET returnArg = "Something Went Wrong Adding the Online Customer!";

    /* check the customerID is valid */
    SELECT COUNT(*) INTO nicExistArg FROM `individual` WHERE `Nic`= nid;

    IF nicExistArg <> 0 THEN

        /*check customer has online account*/
        SELECT COUNT(*) INTO nicExistInOnlineAccount FROM `customer_login` WHERE `Username`= nid;
                

        IF nicExistInOnlineAccount = 0 THEN

            /*get customer id from the customer table*/
            SELECT DISTINCT `CustomerId` INTO getCustomerID FROM `individual` WHERE `Nic`= nid;
           
			INSERT INTO `customer_login`(`CustomerId`, `Username`, `Password`, `Status`) VALUES (getCustomerID,nid,random_string(8,'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'),"1");
            COMMIT;
             SET returnArg = "success";

        ELSE
            SET returnArg = "Account already exists";
        END  IF;

    ELSE
    	SET returnArg = "NIC is not registrerd!";
    END IF;

    SELECT returnArg;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createIndividualLoanRequest` (IN `NICArg` CHAR(10), IN `AmountArg` DECIMAL(8,2), IN `branchIdArg` VARCHAR(40), IN `EmployeeIdArg` VARCHAR(40), IN `loanTypeArg` VARCHAR(40), IN `requestDateArg` DATE, IN `EmploymentSectorArg` ENUM("PRIVATE","GOV","SELF"), IN `EmploymentTypeArg` ENUM("PER","TEMP"), IN `GrossSalaryArg` DECIMAL(10,2), IN `NetSalaryArg` DECIMAL(10,2), IN `ProfessionArg` VARCHAR(40))  MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE CustomerIdArg VARCHAR(40);
DECLARE LoanTypeIdArg VARCHAR(10);
DECLARE returnArg VARCHAR(100);


/* Set Default Statement */
SET returnArg = "Something Went Wrong while Creating a Loan Request!";

/* check the customer is already registred */
SELECT `CustomerId` INTO CustomerIdArg FROM `individual` WHERE `Nic`= NICArg;

/* check for the existance of the customer */
IF LENGTH(CustomerIdArg) > 0 THEN

	/* get the loan type id from loan_type table */
	SELECT `LoanTypeId` INTO LoanTypeIdArg FROM `loan_type` WHERE `LoanTypeName`= loanTypeArg;

    /* Add the data of loan request to the loan_request Table */
    INSERT INTO `loan_request`(`CustomerId`, `BranchId`, `Amount`, `EmployeeId`, `GrossSalary`, `NetSalary`, `EmploymentSector`, `EmploymentType`, `Profession`, `LoanTypeId`, `requestDate`)
    VALUES (CustomerIdArg, branchIdArg, AmountArg, EmployeeIdArg, GrossSalaryArg, NetSalaryArg, EmploymentSectorArg, EmploymentTypeArg, ProfessionArg, LoanTypeIdArg, requestDateArg );

    SET returnArg = "Success";
    COMMIT;

ELSE
    SET returnArg = "!";
END IF;

SELECT returnArg;
	
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `fixedDepositOpen` (IN `fdAccountNumberArg` VARCHAR(40), IN `savingsAccountNumberArg` VARCHAR(40), IN `customerNICArg` CHAR(10), IN `depositAmountArg` DECIMAL(12,2), IN `depositDateArg` DATE, IN `matuarityDateArg` DATE, IN `fdTypeArg` VARCHAR(10))  MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
 DECLARE customerAccountExistanceArg int(2);
 DECLARE customerIdArg VARCHAR(40);
 DECLARE returnArg VARCHAR(255);
 DECLARE savingsStatusArg CHAR(1);

/* BEGIN TRANSACTION */
START TRANSACTION;

/* Set Default Statement */
SET returnArg = "Something Went Wrong During FD Open!";

/* select the customerId from the table */
SELECT `CustomerId` INTO customerIdArg FROM `individual` WHERE `Nic` = customerNICArg;

/* check for the existance of the customer */
IF LENGTH(customerIdArg) > 0 THEN

    /* assign the current balance and account type to variables */
    SELECT COUNT(*) INTO customerAccountExistanceArg FROM `savings_customers` WHERE `AccountNumber` = savingsAccountNumberArg AND `CustomerId` = customerIdArg;

    IF customerAccountExistanceArg > 0 THEN
    
    	/* check the savings is active one */
        IF savingsStatusArg = '1' THEN
            /* insert the values into fd account table */
            INSERT INTO `fixed_deposit_account`(`FDAccountNumber`, `SavingsAccountNumber`, `CustomerId`, `DepositAmount`, `DepositDate`, `MaturityDate`, `FDType`) VALUES
            (fdAccountNumberArg, savingsAccountNumberArg, customerIdArg, depositAmountArg, depositDateArg, matuarityDateArg, fdTypeArg);

            SET returnArg = "Success";
            COMMIT;
            
        ELSE
        	SET returnArg = "Savings Account is Closed One";
        END IF;
    ELSE
    	SET returnArg = "Account Number Doesn't match to the customer";
	END IF;

ELSE
	SET returnArg = "Customer nic doesn't exist!";
END IF;
	SELECT returnArg;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `openChildSavingsAccount` (IN `savingsTypeArg` VARCHAR(50), IN `accNumberArg` VARCHAR(40), IN `depositAmountArg` DECIMAL(12,2), IN `empolyeeIdArg` VARCHAR(40), IN `guardianNICArg` CHAR(10), IN `childFullNameArg` VARCHAR(255), IN `childDOB` DATE, IN `branchIdArg` VARCHAR(40))  MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE returnArg VARCHAR(100);
DECLARE accounttypeIdArg VARCHAR(20);
DECLARE guardianIdArg VARCHAR(40);
DECLARE childIdArg VARCHAR(40);
DECLARE trasactionIdArg VARCHAR(40);
    
/* BEGIN TRANSACTION */
START TRANSACTION;

SET returnArg = "Something Wend wrong during Account Open";

/* check the guradian nic is valid or not */
SELECT `CustomerId` INTO guardianIdArg FROM `individual` WHERE `Nic` = guardianNICArg;
IF LENGTH(guardianIdArg) > 0 THEN
	
    /* select the account Type Id */
    SELECT `AccountTypeId` INTO accounttypeIdArg FROM `savings_accounts_type` WHERE `AccountTypeName` = savingsTypeArg;
    
    /* insert the child in to customer and children tables */
    SELECT UUID() INTO childIdArg;
    
    INSERT INTO `customer`(`CustomerId`, `CustomerType`) VALUES (childIdArg, "Child");

	INSERT INTO `children`(`CustomerId`, `FullName`, `BirthDate`, `GuardianId`) VALUES (childIdArg, childFullNameArg, childDOB, guardianIdArg);
    
    /* open the savings account */
    INSERT INTO `savings_account`(`AccountNumber`, `BranchId`, `AccountTypeId`, `Balance`, `Status`) 
    VALUES (accNumberArg, branchIdArg, accounttypeIdArg, depositAmountArg, "1");
    
    INSERT INTO `savings_open`(`AccountNumber`, `OpenEmployeeId`, `OpenDate`) VALUES (accNumberArg, empolyeeIdArg, CURDATE());
    
    SELECT UUID() INTO trasactionIdArg;
    
    INSERT INTO `savings_transaction`(`TransactionId`, `AccountNumber`, `TransactionDate`, `Amount`, `Teller`, `TransactionType`) 
    VALUES (trasactionIdArg, accNumberArg, CURDATE(), depositAmountArg, "EMP",	"Deposit");
    
    INSERT INTO `employee_savings_transaction`(`EmployeeId`, `TransactionId`) VALUES (empolyeeIdArg,trasactionIdArg);
    
    INSERT INTO `savings_customers`(`AccountNumber`, `CustomerId`) VALUES (accNumberArg, childIdArg);
    
    SET returnArg = "Success";
    COMMIT;
ELSE
	SET returnArg = "Guardian Doesn't exist";
END IF;

	SELECT returnArg;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `removeNormalEmployee` (IN `empIdArg` VARCHAR(40), IN `branchIdArg` VARCHAR(40))  MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE returnArg VARCHAR(100);
DECLARE id VARCHAR(40);
    
/* BEGIN TRANSACTION */
START TRANSACTION;

SELECT `EmployeeId` INTO id FROM `employee` WHERE `Nic`= empIdArg;

/* update the normal employee history table */
UPDATE `normal_employees_history` SET `LeftDate`= CURDATE() WHERE `EmployeeId`= id AND `BranchId`= branchIdArg AND `LeftDate` IS NULL;

/* update the employee login table */
UPDATE `employee_login` SET `Status`= "0" WHERE `EmployeeId` = id;

/* update the employee table */
UPDATE `employee` SET `BranchId`= NULL  WHERE `EmployeeId` = id;

COMMIT;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `savingsClose` (IN `accountNumberArg` VARCHAR(40), IN `dateOfClosingArg` DATE, IN `empId` VARCHAR(40))  MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
 DECLARE currentBalanceArg DECIMAL(12,2);
 DECLARE accountTypeArg VARCHAR(40);
 DECLARE currentStatusArg CHAR(1);
 DECLARE validAccountArg int(2);
 DECLARE returnArg VARCHAR(255);
 DECLARE transactionIdArg VARCHAR(40);
 DECLARE numberOfRealtedFdAccountsArg int(2);


/* BEGIN TRANSACTION */
START TRANSACTION;

/* Set Default Statement */
SET returnArg = "Something Went During Withdrawal Try Again!";

/* assign the current balance and account type to variables */
SELECT COUNT(*) INTO validAccountArg FROM `savings_account` WHERE `AccountNumber`= accountNumberArg ;

IF validAccountArg > 0 THEN

    /* assign the current balance and account status to variables */
    SELECT  `Status` INTO currentStatusArg FROM `savings_account` WHERE `AccountNumber`= accountNumberArg;

    IF currentStatusArg = "1" THEN
    
    	/* check if fd account is linked to this acccount */
        SELECT COUNT(*) INTO numberOfRealtedFdAccountsArg FROM `fixed_deposit_account` WHERE `SavingsAccountNumber` = accountNumberArg;
        
        IF numberOfRealtedFdAccountsArg = 0 THEN

            SELECT `Balance` INTO currentBalanceArg FROM `savings_account` WHERE `AccountNumber`= accountNumberArg;

            SELECT UUID() INTO transactionIdArg;

            /* update the savings account balance and Status */
            UPDATE `savings_account` SET `Balance`= 0.00, `Status`= '0'   WHERE  `AccountNumber`= accountNumberArg;

            /* insert the data into transaction table */
            INSERT INTO `savings_transaction`(`TransactionId`, `AccountNumber`, `TransactionDate`, `Amount`, `Teller`, `TransactionType`)
            VALUES (transactionIdArg , accountNumberArg, dateOfClosingArg, currentBalanceArg, "EMP", "Withdrawal");

            INSERT INTO `employee_savings_transaction`(`EmployeeId`, `TransactionId`) VALUES (empId, transactionIdArg);

            /* update the savings account closing tabel */
            INSERT INTO `savings_close`(`AccountNumber`, `CloseEmployeeId`, `CloseDate`)
            VALUES (accountNumberArg, empId, dateOfClosingArg);

            SET returnArg = "Success";
            COMMIT;
            
		ELSE
        	SET returnArg = "FD account has been linked to this account";
        END IF;

	ELSE
        SET returnArg = "Account Already Closed!";
    END IF;
ELSE
	SET returnArg = "Incorrect Account Number!";
END IF;
	SELECT returnArg;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `savingsDeposit` (IN `accountNumberArg` VARCHAR(40), IN `withdrawAmountArg` DECIMAL(10,2), IN `dateOfWithdrawalArg` DATE, IN `tellerArg` VARCHAR(25), IN `empId` VARCHAR(40))  MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE currentBalanceArg DECIMAL(12,2);
DECLARE accountTypeArg VARCHAR(40);
DECLARE lastTransactionIdArg VARCHAR(40);
DECLARE validAccountArg int(2);
DECLARE returnArg VARCHAR(255);
DECLARE accountStatusArg int(2);

 
/* BEGIN TRANSACTION */
START TRANSACTION;

/* Set Default Statement */
SET returnArg = "Something Went During Withdrawal Try Again!";

/* assign the current balance and account type to variables */
SELECT COUNT(*) INTO validAccountArg FROM `savings_account` WHERE `AccountNumber`= accountNumberArg ;

IF validAccountArg > 0 THEN 
	
    /* Check the account is closed or not */
	SELECT COUNT(*) INTO accountStatusArg FROM `savings_account` WHERE `AccountNumber`= accountNumberArg AND `Status`="1";
    
    IF accountStatusArg > 0 THEN
    
       /* assign the current balance to variables */
       SELECT `Balance` INTO currentBalanceArg FROM `savings_account` WHERE `AccountNumber`= accountNumberArg AND `Status`="1";

        /* update the new account balance */
        SELECT currentBalanceArg + withdrawAmountArg into currentBalanceArg ;

        SELECT UUID() INTO lastTransactionIdArg;

        /* update the savings account table */
        UPDATE `savings_account` SET `Balance`= currentBalanceArg   WHERE  `AccountNumber`= accountNumberArg;

        /* insert the data into transaction table */
        INSERT INTO `savings_transaction`(`TransactionId`, `AccountNumber`, `TransactionDate`, `Amount`, `Teller`, `TransactionType`)
        VALUES (lastTransactionIdArg, accountNumberArg, dateOfWithdrawalArg, withdrawAmountArg, tellerArg, "Deposit");
        
        /* check if the deposit done by employee */
        IF tellerArg = "EMP" THEN
        	INSERT INTO `employee_savings_transaction`(`EmployeeId`, `TransactionId`) VALUES (empId, lastTransactionIdArg);
                SET returnArg = "Success";
				COMMIT;
		ELSE
        	SET returnArg = "Success";
            COMMIT;
		END IF;
                    
	ELSE
        SET returnArg = "This is an closed Account!";
    END IF;            
         
ELSE
	SET returnArg = "Incorrect Account Number!";
END IF;
        
	SELECT returnArg;
                    
                                    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `savingsWithdraw` (IN `accountNumberArg` VARCHAR(40), IN `withdrawAmountArg` DECIMAL(10,2), IN `dateOfWithdrawalArg` DATE, IN `tellerArg` VARCHAR(25), IN `empId` VARCHAR(40))  MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
 DECLARE currentBalanceArg DECIMAL(12,2);
 DECLARE accountTypeArg VARCHAR(40);
 DECLARE minimumBalanceArg DECIMAL(10,2);
 DECLARE numberOfWithdrawalsArg INT(3);
 DECLARE lastTransactionIdArg VARCHAR(40);
 DECLARE returnArg VARCHAR(255);
 DECLARE validAccountArg int(2);
 DECLARE accountStatusArg int(2);


/*	BEGIN TRANSACTION */
START TRANSACTION;

/* Set Default Statement */
SET returnArg = "Something Went During Withdrawal Try Again!";

/* check for the existence of account id */
SELECT COUNT(*) INTO validAccountArg FROM `savings_account` WHERE `AccountNumber`= accountNumberArg;

IF validAccountArg > 0 THEN

	 /* Check the account is closed or not */
	SELECT COUNT(*) INTO accountStatusArg FROM `savings_account` WHERE `AccountNumber`= accountNumberArg AND `Status`="1";

    IF accountStatusArg > 0 THEN

        /* select the account type */
        SELECT `AccountTypeId` INTO accountTypeArg FROM `savings_account` WHERE `AccountNumber`= accountNumberArg;

        /* check for the account type to withdraw */
        IF (accountTypeArg = "SA_TYPE_004" OR accountTypeArg = "SA_TYPE_003") THEN

            /* assign the current balance and account type to variables */
            SELECT `Balance` INTO currentBalanceArg FROM `savings_account` WHERE `AccountNumber`= accountNumberArg;

            /* get the minimum balance for the account */
            SELECT `MinimumBalance` INTO minimumBalanceArg FROM `savings_accounts_type` WHERE `AccountTypeId`= accountTypeArg;

            /* update the new account balance */
            SELECT currentBalanceArg - withdrawAmountArg into currentBalanceArg ;

            IF currentBalanceArg >= minimumBalanceArg THEN

                /* get the number of withdrawals in this month */
                SELECT COUNT(*) INTO numberOfWithdrawalsArg FROM `savings_transaction`
                WHERE `AccountNumber`= accountNumberArg AND `TransactionType`="Withdrawal" AND MONTH(`TransactionDate`) = MONTH(dateOfWithdrawalArg)
                AND TransactionType != "SYSTEM" ;

                IF numberOfWithdrawalsArg < 5 THEN

                   /* update the savings account table */
                    UPDATE `savings_account` SET `Balance`= currentBalanceArg   WHERE  `AccountNumber`= accountNumberArg;

                    SELECT UUID() INTO lastTransactionIdArg;

                    /* insert the data into transaction table */
                    INSERT INTO `savings_transaction`(`TransactionId`, `AccountNumber`, `TransactionDate`, `Amount`, `Teller`, `TransactionType`)
                    VALUES (lastTransactionIdArg, accountNumberArg, dateOfWithdrawalArg, withdrawAmountArg, tellerArg, "Withdrawal");

                    /* check if the transaction done by employee */
                    IF tellerArg = "EMP" THEN

                        /* insert the values into transaction employee table */
                        INSERT INTO `employee_savings_transaction`(`EmployeeId`, `TransactionId`) VALUES (empId, lastTransactionIdArg);
                            SET returnArg = "Success";
                            COMMIT;

                    ELSE
                        SET returnArg = "Success";
                        COMMIT;
                    END IF;

                ELSE
                    SET returnArg = "Number of Withdrawals reached for this month" ;
                END IF;

            ELSE
                SET returnArg = "Not Enough money in the account!";
            END IF;

        ELSE
            SET returnArg = "Cannot Withdraw money from this account type!";
        END IF;
	ELSE
        SET returnArg = "This is an closed Account!";
    END IF;
ELSE
	SET returnArg = "Wrong Account Number Doesn't exist!";
END IF;
    SELECT returnArg;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `viewCurrentEmployees` (`branchIdArg` VARCHAR(40))  MODIFIES SQL DATA
BEGIN

	/* select the normal employee details */
    SELECT EmployeeId, FirstName, LastName, Nic, JoinedDate ,"Normal Employee" AS designation FROM employee 
        INNER JOIN 	normal_employees_history USING(EmployeeId) 
        WHERE employee.BranchId = branchIdArg AND normal_employees_history.LeftDate IS NULL 

        UNION

	/* select the managers details */
    SELECT EmployeeId, FirstName,
        LastName, Nic, JoinedDate, "Branch Manager" AS designation FROM employee 
        INNER JOIN branch_manager USING(EmployeeId) INNER JOIN branch_managers_history USING(ManagerId) 
        WHERE employee.BranchId = branchIdArg AND branch_managers_history.LeftDate IS NULL;

END$$

--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `random_string` (`length` SMALLINT(3), `seed` VARCHAR(255)) RETURNS VARCHAR(255) CHARSET utf8 NO SQL
BEGIN
    SET @output = '';

    IF seed IS NULL OR seed = '' THEN SET seed = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'; END IF;

    SET @rnd_multiplier = LENGTH(seed);

    WHILE LENGTH(@output) < length DO
        # Select random character and add to output
        SET @output = CONCAT(@output, SUBSTRING(seed, RAND() * (@rnd_multiplier + 1), 1));
    END WHILE;

    RETURN @output;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `atm_withdraw`
--

CREATE TABLE `atm_withdraw` (
  `WithdrawId` varchar(40) NOT NULL,
  `CardNumber` varchar(16) NOT NULL,
  `WithdrawDate` datetime NOT NULL,
  `Amount` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `BranchId` varchar(40) NOT NULL,
  `BranchName` varchar(50) NOT NULL,
  `PostalAddress` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`BranchId`, `BranchName`, `PostalAddress`) VALUES
('BOSL_001_COL_NUG', 'NUGEGODA', 'No.56, Bank Of Sri Lanka, High Level Road, Nugegoda'),
('BOSL_002_COL_MAH', 'Maharagama', 'No.10, High Level Road, Maharagama'),
('BOSL_003_GMP_GMP', 'Gampaha', 'No.10, Gampaha Road, Gampaha'),
('BOSL_004_COL_CEN', 'MAIN', 'No.20, Walukarama Road, Colomo 07'),
('BOSL_005_COL_BOR', 'Borella', 'No.10, Kota Road, Borella');

-- --------------------------------------------------------

--
-- Table structure for table `branch_manager`
--

CREATE TABLE `branch_manager` (
  `ManagerId` varchar(40) NOT NULL,
  `EmployeeId` varchar(40) DEFAULT NULL,
  `BranchId` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `branch_manager`
--

INSERT INTO `branch_manager` (`ManagerId`, `EmployeeId`, `BranchId`) VALUES
('178af19e-1e20-11ea-b1ca-d8c4971f41ea', 'c44cb195-1e1f-11ea-b1ca-d8c4971f41ea', 'BOSL_001_COL_NUG');

-- --------------------------------------------------------

--
-- Table structure for table `branch_managers_history`
--

CREATE TABLE `branch_managers_history` (
  `ManagerId` varchar(40) NOT NULL,
  `BranchId` varchar(40) NOT NULL,
  `JoinedDate` date NOT NULL,
  `LeftDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `branch_managers_history`
--

INSERT INTO `branch_managers_history` (`ManagerId`, `BranchId`, `JoinedDate`, `LeftDate`) VALUES
('178af19e-1e20-11ea-b1ca-d8c4971f41ea', 'BOSL_001_COL_NUG', '2019-12-14', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `children`
--

CREATE TABLE `children` (
  `CustomerId` varchar(40) NOT NULL,
  `FullName` varchar(255) NOT NULL,
  `BirthDate` date DEFAULT NULL,
  `GuardianId` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `children`
--

INSERT INTO `children` (`CustomerId`, `FullName`, `BirthDate`, `GuardianId`) VALUES
('42c07756-20a3-11ea-b1ca-d8c4971f41ea', 'Danapal Daj', '2012-12-13', 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea');

-- --------------------------------------------------------

--
-- Table structure for table `current_account`
--

CREATE TABLE `current_account` (
  `AccountNumber` varchar(40) NOT NULL,
  `BranchId` varchar(40) NOT NULL,
  `CustomerId` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `current_account_close`
--

CREATE TABLE `current_account_close` (
  `AccountNumber` varchar(40) NOT NULL,
  `CloseEmployeeId` varchar(40) NOT NULL,
  `CloseDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `current_account_open`
--

CREATE TABLE `current_account_open` (
  `AccountNumber` varchar(40) NOT NULL,
  `OpenEmployeeId` varchar(40) NOT NULL,
  `OpenDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `current_transaction`
--

CREATE TABLE `current_transaction` (
  `TransactionId` varchar(40) NOT NULL,
  `EmployeeId` varchar(40) NOT NULL,
  `AccountNumber` varchar(40) NOT NULL,
  `TransactionDate` date NOT NULL,
  `Amount` decimal(10,2) NOT NULL,
  `ChequeNumber` varchar(40) NOT NULL,
  `TransactionType` enum('Withdrawal','Deposit') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `CustomerId` varchar(40) NOT NULL,
  `CustomerType` enum('Individual','Organization','Child') NOT NULL,
  `Email` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`CustomerId`, `CustomerType`, `Email`) VALUES
('42c07756-20a3-11ea-b1ca-d8c4971f41ea', 'Child', NULL),
('787ae9e2-1bbc-11ea-a1fc-d8c4971f41ea', 'Individual', 'jas@gm.com'),
('ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', 'Individual', 'lak@gmail.com'),
('42c07756-20a3-11ea-b1ca-d8c4971f41ea', 'Child', NULL),
('787ae9e2-1bbc-11ea-a1fc-d8c4971f41ea', 'Individual', 'jas@gm.com'),
('ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', 'Individual', 'lak@gmail.com'),
('42c07756-20a3-11ea-b1ca-d8c4971f41ea', 'Child', NULL),
('787ae9e2-1bbc-11ea-a1fc-d8c4971f41ea', 'Individual', 'jas@gm.com'),
('ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', 'Individual', 'lak@gmail.com'),
('a5a77541-256f-11ea-b492-84ef18bc3d0a', 'Organization', 'hh@ww.ll');

-- --------------------------------------------------------

--
-- Table structure for table `customer_login`
--

CREATE TABLE `customer_login` (
  `CustomerId` varchar(40) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(256) NOT NULL,
  `Status` enum('0','1') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer_login`
--

INSERT INTO `customer_login` (`CustomerId`, `Username`, `Password`, `Status`) VALUES
('787ae9e2-1bbc-11ea-a1fc-d8c4971f41ea', '123456789V', '9j3t9vyw', '1'),
('ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '980083837V', 'f0osl4ry', '1');

-- --------------------------------------------------------

--
-- Table structure for table `debit_card`
--

CREATE TABLE `debit_card` (
  `CardNumber` char(16) NOT NULL,
  `PinNumber` char(4) NOT NULL,
  `AccountNumber` varchar(40) NOT NULL,
  `IssuedDate` date NOT NULL,
  `ExpiryDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `EmployeeId` varchar(40) NOT NULL,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `BranchId` varchar(40) DEFAULT NULL,
  `Nic` char(10) NOT NULL,
  `Email` varchar(250) NOT NULL,
  `BirthDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`EmployeeId`, `FirstName`, `LastName`, `BranchId`, `Nic`, `Email`, `BirthDate`) VALUES
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'Alan', 'Walker', 'BOSL_001_COL_NUG', '123456789V', 'alanwalker@gmail.com', '1992-01-10'),
('c44cb195-1e1f-11ea-b1ca-d8c4971f41ea', 'Brnach', 'Manager', 'BOSL_001_COL_NUG', '789456123V', 'branchmanager@gmail.com', '1992-01-10'),
('c4abc02b-20b1-11ea-b1ca-d8c4971f41ea', 'Niljnjs', 'jnjbnjbji', NULL, '999999999V', 'sjsonob@hbhb.com', '1995-12-12'),
('ce49779d-1ea4-11ea-b1ca-d8c4971f41ea', 'Aser', 'Aspire', NULL, '777788888V', 'asp@gmail.com', '1993-12-03'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'Alan', 'Walker', 'BOSL_001_COL_NUG', '123456789V', 'alanwalker@gmail.com', '1992-01-10'),
('c44cb195-1e1f-11ea-b1ca-d8c4971f41ea', 'Brnach', 'Manager', 'BOSL_001_COL_NUG', '789456123V', 'branchmanager@gmail.com', '1992-01-10'),
('c4abc02b-20b1-11ea-b1ca-d8c4971f41ea', 'Niljnjs', 'jnjbnjbji', NULL, '999999999V', 'sjsonob@hbhb.com', '1995-12-12'),
('ce49779d-1ea4-11ea-b1ca-d8c4971f41ea', 'Aser', 'Aspire', NULL, '777788888V', 'asp@gmail.com', '1993-12-03'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'Alan', 'Walker', 'BOSL_001_COL_NUG', '123456789V', 'alanwalker@gmail.com', '1992-01-10'),
('c44cb195-1e1f-11ea-b1ca-d8c4971f41ea', 'Brnach', 'Manager', 'BOSL_001_COL_NUG', '789456123V', 'branchmanager@gmail.com', '1992-01-10'),
('c4abc02b-20b1-11ea-b1ca-d8c4971f41ea', 'Niljnjs', 'jnjbnjbji', NULL, '999999999V', 'sjsonob@hbhb.com', '1995-12-12'),
('ce49779d-1ea4-11ea-b1ca-d8c4971f41ea', 'Aser', 'Aspire', NULL, '777788888V', 'asp@gmail.com', '1993-12-03');

-- --------------------------------------------------------

--
-- Table structure for table `employee_login`
--

CREATE TABLE `employee_login` (
  `EmployeeId` varchar(40) NOT NULL,
  `UserName` varchar(50) NOT NULL,
  `Password` varchar(500) NOT NULL,
  `Status` enum('1','0') NOT NULL,
  `Type` enum('Normal','BranchManager') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employee_login`
--

INSERT INTO `employee_login` (`EmployeeId`, `UserName`, `Password`, `Status`, `Type`) VALUES
('ce49779d-1ea4-11ea-b1ca-d8c4971f41ea', '777788888V', 'BOSL', '0', 'Normal'),
('c4abc02b-20b1-11ea-b1ca-d8c4971f41ea', '999999999V', 'BOSL', '0', 'Normal'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'admin', 'admin', '1', 'Normal'),
('c44cb195-1e1f-11ea-b1ca-d8c4971f41ea', 'manager', 'manager', '1', 'BranchManager'),
('ce49779d-1ea4-11ea-b1ca-d8c4971f41ea', '777788888V', 'BOSL', '0', 'Normal'),
('c4abc02b-20b1-11ea-b1ca-d8c4971f41ea', '999999999V', 'BOSL', '0', 'Normal'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'admin', 'admin', '1', 'Normal'),
('c44cb195-1e1f-11ea-b1ca-d8c4971f41ea', 'manager', 'manager', '1', 'BranchManager'),
('ce49779d-1ea4-11ea-b1ca-d8c4971f41ea', '777788888V', 'BOSL', '0', 'Normal'),
('c4abc02b-20b1-11ea-b1ca-d8c4971f41ea', '999999999V', 'BOSL', '0', 'Normal'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'admin', 'admin', '1', 'Normal'),
('c44cb195-1e1f-11ea-b1ca-d8c4971f41ea', 'manager', 'manager', '1', 'BranchManager');

-- --------------------------------------------------------

--
-- Table structure for table `employee_savings_transaction`
--

CREATE TABLE `employee_savings_transaction` (
  `EmployeeId` varchar(40) NOT NULL,
  `TransactionId` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employee_savings_transaction`
--

INSERT INTO `employee_savings_transaction` (`EmployeeId`, `TransactionId`) VALUES
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '0199263d-1d4d-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '12da863f-1c31-11ea-a1fc-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '13e5bbd5-1cc5-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '18e478d0-1d46-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '3b6f4360-1c31-11ea-a1fc-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '42d9c0e5-20a3-11ea-b1ca-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '62798dc1-1d4d-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '6a0c0392-1c35-11ea-a1fc-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '7d104c41-1d4b-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'a220b756-1cc5-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'ae66343a-1d4e-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'b510131a-1d4e-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'bc3a7d67-1d4b-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'dc2e7316-1d4c-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'de3c6d1a-1cc5-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '0199263d-1d4d-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '12da863f-1c31-11ea-a1fc-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '13e5bbd5-1cc5-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '18e478d0-1d46-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '3b6f4360-1c31-11ea-a1fc-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '42d9c0e5-20a3-11ea-b1ca-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '62798dc1-1d4d-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '6a0c0392-1c35-11ea-a1fc-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '7d104c41-1d4b-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'a220b756-1cc5-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'ae66343a-1d4e-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'b510131a-1d4e-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'bc3a7d67-1d4b-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'dc2e7316-1d4c-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'de3c6d1a-1cc5-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '0199263d-1d4d-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '12da863f-1c31-11ea-a1fc-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '13e5bbd5-1cc5-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '18e478d0-1d46-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '3b6f4360-1c31-11ea-a1fc-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '42d9c0e5-20a3-11ea-b1ca-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '62798dc1-1d4d-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '6a0c0392-1c35-11ea-a1fc-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '7d104c41-1d4b-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'a220b756-1cc5-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'ae66343a-1d4e-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'b510131a-1d4e-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'bc3a7d67-1d4b-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'dc2e7316-1d4c-11ea-911d-d8c4971f41ea'),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'de3c6d1a-1cc5-11ea-911d-d8c4971f41ea');

-- --------------------------------------------------------

--
-- Table structure for table `fixed_deposit_account`
--

CREATE TABLE `fixed_deposit_account` (
  `FDAccountNumber` varchar(40) NOT NULL,
  `SavingsAccountNumber` varchar(40) NOT NULL,
  `CustomerId` varchar(40) NOT NULL,
  `DepositAmount` decimal(12,2) NOT NULL,
  `DepositDate` date NOT NULL,
  `MaturityDate` date NOT NULL,
  `FDType` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fixed_deposit_account`
--

INSERT INTO `fixed_deposit_account` (`FDAccountNumber`, `SavingsAccountNumber`, `CustomerId`, `DepositAmount`, `DepositDate`, `MaturityDate`, `FDType`) VALUES
('FD0001', 'ABC123', 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '10000.00', '2019-12-13', '2020-06-10', 'Type_1'),
('FD0002', 'ABC123', 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '15000.00', '2019-12-13', '2020-06-10', 'Type_1'),
('FD0001', 'ABC123', 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '10000.00', '2019-12-13', '2020-06-10', 'Type_1'),
('FD0002', 'ABC123', 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '15000.00', '2019-12-13', '2020-06-10', 'Type_1'),
('FD0001', 'ABC123', 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '10000.00', '2019-12-13', '2020-06-10', 'Type_1'),
('FD0002', 'ABC123', 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '15000.00', '2019-12-13', '2020-06-10', 'Type_1');

-- --------------------------------------------------------

--
-- Table structure for table `fixed_deposit_type`
--

CREATE TABLE `fixed_deposit_type` (
  `FDType` varchar(10) NOT NULL,
  `Duration` int(4) NOT NULL,
  `InterestRate` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fixed_deposit_type`
--

INSERT INTO `fixed_deposit_type` (`FDType`, `Duration`, `InterestRate`) VALUES
('Type_1', 6, '0.13'),
('Type_2', 12, '0.14'),
('Type_3', 36, '0.15'),
('Type_1', 6, '0.13'),
('Type_2', 12, '0.14'),
('Type_3', 36, '0.15'),
('Type_1', 6, '0.13'),
('Type_2', 12, '0.14'),
('Type_3', 36, '0.15');

-- --------------------------------------------------------

--
-- Table structure for table `individual`
--

CREATE TABLE `individual` (
  `CustomerId` varchar(40) NOT NULL,
  `Nic` char(10) NOT NULL,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `HouseNumber` varchar(50) NOT NULL,
  `StreetOne` varchar(50) NOT NULL,
  `StreetTwo` varchar(50) DEFAULT NULL,
  `Town` varchar(50) NOT NULL,
  `District` varchar(50) NOT NULL,
  `PostalCode` char(5) NOT NULL,
  `Gender` enum('Male','Female') NOT NULL,
  `Birthday` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `individual`
--

INSERT INTO `individual` (`CustomerId`, `Nic`, `FirstName`, `LastName`, `HouseNumber`, `StreetOne`, `StreetTwo`, `Town`, `District`, `PostalCode`, `Gender`, `Birthday`) VALUES
('787ae9e2-1bbc-11ea-a1fc-d8c4971f41ea', '123456789V', 'Jason', 'Holder', '121', 'jnij', 'njn', 'nnj', 'onn', '77777', 'Male', '1952-12-03'),
('ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '980083837V', 'Laskshan ', 'Weerasinghe', '12', 'st', 'sas', 'sadd', 'xsccac', '11111', 'Male', '1999-01-08'),
('787ae9e2-1bbc-11ea-a1fc-d8c4971f41ea', '123456789V', 'Jason', 'Holder', '121', 'jnij', 'njn', 'nnj', 'onn', '77777', 'Male', '1952-12-03'),
('ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '980083837V', 'Laskshan ', 'Weerasinghe', '12', 'st', 'sas', 'sadd', 'xsccac', '11111', 'Male', '1999-01-08'),
('787ae9e2-1bbc-11ea-a1fc-d8c4971f41ea', '123456789V', 'Jason', 'Holder', '121', 'jnij', 'njn', 'nnj', 'onn', '77777', 'Male', '1952-12-03'),
('ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', '980083837V', 'Laskshan ', 'Weerasinghe', '12', 'st', 'sas', 'sadd', 'xsccac', '11111', 'Male', '1999-01-08');

-- --------------------------------------------------------

--
-- Table structure for table `loan`
--

CREATE TABLE `loan` (
  `LoanId` varchar(40) NOT NULL,
  `LoanTypeId` varchar(10) NOT NULL,
  `Amount` decimal(8,2) NOT NULL,
  `SettlementPeriod` int(3) NOT NULL,
  `NoOfSettlements` int(3) NOT NULL,
  `PaidSettlements` decimal(8,2) NOT NULL,
  `LoanStatus` enum('FullyPaid','Current','Late','Cancelled') NOT NULL,
  `InstallmentType` enum('MONTHLY','PERIODICAL') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `loan_installment_history`
--

CREATE TABLE `loan_installment_history` (
  `InstallmentId` varchar(40) NOT NULL,
  `LoanId` varchar(40) DEFAULT NULL,
  `InstallmentDate` date DEFAULT NULL,
  `Amount` decimal(7,2) DEFAULT NULL,
  `DueDate` date NOT NULL,
  `LateCharges` decimal(7,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `loan_request`
--

CREATE TABLE `loan_request` (
  `RequestId` int(40) NOT NULL,
  `CustomerId` varchar(40) COLLATE utf8mb4_croatian_ci NOT NULL,
  `BranchId` varchar(40) COLLATE utf8mb4_croatian_ci NOT NULL,
  `Amount` decimal(8,2) NOT NULL,
  `ApprovedStatus` enum('APPROVED','PENDING','REJECTED') COLLATE utf8mb4_croatian_ci DEFAULT 'PENDING',
  `EmployeeId` varchar(40) COLLATE utf8mb4_croatian_ci NOT NULL,
  `GrossSalary` decimal(10,2) NOT NULL,
  `NetSalary` decimal(10,2) NOT NULL,
  `LoanId` varchar(40) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `EmploymentSector` enum('PRIVATE','GOV','SELF') COLLATE utf8mb4_croatian_ci NOT NULL,
  `EmploymentType` enum('PER','TEMP') COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `Profession` text COLLATE utf8mb4_croatian_ci NOT NULL,
  `LoanTypeId` varchar(10) COLLATE utf8mb4_croatian_ci NOT NULL,
  `requestDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

--
-- Dumping data for table `loan_request`
--

INSERT INTO `loan_request` (`RequestId`, `CustomerId`, `BranchId`, `Amount`, `ApprovedStatus`, `EmployeeId`, `GrossSalary`, `NetSalary`, `LoanId`, `EmploymentSector`, `EmploymentType`, `Profession`, `LoanTypeId`, `requestDate`) VALUES
(1, 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea', 'BOSL_001_COL_NUG', '1555.00', 'PENDING', '23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '122.00', '158.00', NULL, 'GOV', 'PER', 'Bajnji', '1', '2019-12-22');

-- --------------------------------------------------------

--
-- Table structure for table `loan_type`
--

CREATE TABLE `loan_type` (
  `LoanTypeId` varchar(10) NOT NULL,
  `LoanTypeName` varchar(32) NOT NULL,
  `InterestRate` decimal(4,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `loan_type`
--

INSERT INTO `loan_type` (`LoanTypeId`, `LoanTypeName`, `InterestRate`) VALUES
('1', 'Educational', '12.50'),
('2', 'Housing', '12.50'),
('3', 'Personal', '14.50'),
('4', 'Vehicle', '17.50');

-- --------------------------------------------------------

--
-- Table structure for table `normal_employee`
--

CREATE TABLE `normal_employee` (
  `EmployeeId` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `normal_employee`
--

INSERT INTO `normal_employee` (`EmployeeId`) VALUES
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea'),
('c4abc02b-20b1-11ea-b1ca-d8c4971f41ea'),
('ce49779d-1ea4-11ea-b1ca-d8c4971f41ea');

-- --------------------------------------------------------

--
-- Table structure for table `normal_employees_history`
--

CREATE TABLE `normal_employees_history` (
  `EmployeeId` varchar(40) NOT NULL,
  `BranchId` varchar(40) NOT NULL,
  `JoinedDate` date NOT NULL,
  `LeftDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `normal_employees_history`
--

INSERT INTO `normal_employees_history` (`EmployeeId`, `BranchId`, `JoinedDate`, `LeftDate`) VALUES
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'BOSL_001_COL_NUG', '2019-11-29', NULL),
('23adad46-1bb9-11ea-a1fc-d8c4971f41ea', 'BOSL_001_COL_NUG', '2019-12-17', NULL),
('c4abc02b-20b1-11ea-b1ca-d8c4971f41ea', 'BOSL_001_COL_NUG', '2019-12-17', '2019-12-17'),
('ce49779d-1ea4-11ea-b1ca-d8c4971f41ea', 'BOSL_001_COL_NUG', '2019-12-15', '2019-12-17'),
('ce49779d-1ea4-11ea-b1ca-d8c4971f41ea', 'BOSL_001_COL_NUG', '2019-12-17', '2019-12-17');

-- --------------------------------------------------------

--
-- Table structure for table `online_loan`
--

CREATE TABLE `online_loan` (
  `LoanId` varchar(40) DEFAULT NULL,
  `Amount` decimal(7,2) NOT NULL,
  `DepositId` varchar(40) NOT NULL,
  `CustomerId` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `organization`
--

CREATE TABLE `organization` (
  `CustomerId` varchar(40) NOT NULL,
  `OrganizationName` varchar(50) NOT NULL,
  `AuthorizedPerson` varchar(50) NOT NULL,
  `RegisterNumber` varchar(40) NOT NULL,
  `BuildingNumber` varchar(40) NOT NULL,
  `StreetOne` varchar(50) NOT NULL,
  `StreetTwo` varchar(50) DEFAULT NULL,
  `Town` varchar(50) NOT NULL,
  `PostalCode` char(5) NOT NULL,
  `TelephoneNumber` char(10) NOT NULL,
  `AuthorizedPersonNic` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `organization`
--

INSERT INTO `organization` (`CustomerId`, `OrganizationName`, `AuthorizedPerson`, `RegisterNumber`, `BuildingNumber`, `StreetOne`, `StreetTwo`, `Town`, `PostalCode`, `TelephoneNumber`, `AuthorizedPersonNic`) VALUES
('a5a77541-256f-11ea-b492-84ef18bc3d0a', 'jjjjjjjjjj', 'jjj', '1111', '8', 'jyh', 'h', 'hhgtggg', '77777', '0112584787', '444444444V');

-- --------------------------------------------------------

--
-- Table structure for table `savings_account`
--

CREATE TABLE `savings_account` (
  `AccountNumber` varchar(40) NOT NULL,
  `BranchId` varchar(40) NOT NULL,
  `AccountTypeId` varchar(40) NOT NULL,
  `Balance` decimal(12,2) NOT NULL,
  `Status` enum('0','1') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `savings_account`
--

INSERT INTO `savings_account` (`AccountNumber`, `BranchId`, `AccountTypeId`, `Balance`, `Status`) VALUES
('ABC123', 'BOSL_001_COL_NUG', 'SA_TYPE_003', '1000.00', '1'),
('ABCD123', 'BOSL_001_COL_NUG', 'SA_TYPE_003', '10000.00', '1'),
('QWEEQQEQEQE', 'BOSL_001_COL_NUG', 'SA_TYPE_001', '1000.00', '1'),
('QWER123', 'BOSL_001_COL_NUG', 'SA_TYPE_004', '1000.00', '1'),
('QWERT', 'BOSL_001_COL_NUG', 'SA_TYPE_004', '1000.00', '1');

-- --------------------------------------------------------

--
-- Table structure for table `savings_accounts_type`
--

CREATE TABLE `savings_accounts_type` (
  `AccountTypeId` varchar(20) NOT NULL,
  `AccountTypeName` varchar(50) NOT NULL,
  `InterestRate` decimal(5,2) NOT NULL,
  `MinimumAge` int(11) NOT NULL,
  `MaximumAge` int(11) NOT NULL,
  `MinimumBalance` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `savings_accounts_type`
--

INSERT INTO `savings_accounts_type` (`AccountTypeId`, `AccountTypeName`, `InterestRate`, `MinimumAge`, `MaximumAge`, `MinimumBalance`) VALUES
('SA_TYPE_001', 'Children', '0.12', 0, 18, '0.00'),
('SA_TYPE_002', 'Teen', '0.11', 13, 19, '500.00'),
('SA_TYPE_003', '18+', '0.10', 18, 59, '1000.00'),
('SA_TYPE_004', 'Senior Citizen', '0.13', 60, 100, '1000.00');

-- --------------------------------------------------------

--
-- Table structure for table `savings_close`
--

CREATE TABLE `savings_close` (
  `AccountNumber` varchar(40) NOT NULL,
  `CloseEmployeeId` varchar(40) NOT NULL,
  `CloseDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `savings_close`
--

INSERT INTO `savings_close` (`AccountNumber`, `CloseEmployeeId`, `CloseDate`) VALUES
('ABC123', '23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '2019-12-13');

-- --------------------------------------------------------

--
-- Table structure for table `savings_customers`
--

CREATE TABLE `savings_customers` (
  `AccountNumber` varchar(40) NOT NULL,
  `CustomerId` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `savings_customers`
--

INSERT INTO `savings_customers` (`AccountNumber`, `CustomerId`) VALUES
('ABC123', 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea'),
('ABCD123', 'ae29d825-1bb9-11ea-a1fc-d8c4971f41ea'),
('QWEEQQEQEQE', '42c07756-20a3-11ea-b1ca-d8c4971f41ea'),
('QWER123', '787ae9e2-1bbc-11ea-a1fc-d8c4971f41ea'),
('QWERT', '787ae9e2-1bbc-11ea-a1fc-d8c4971f41ea');

-- --------------------------------------------------------

--
-- Table structure for table `savings_open`
--

CREATE TABLE `savings_open` (
  `AccountNumber` varchar(40) NOT NULL,
  `OpenEmployeeId` varchar(40) NOT NULL,
  `OpenDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `savings_open`
--

INSERT INTO `savings_open` (`AccountNumber`, `OpenEmployeeId`, `OpenDate`) VALUES
('ABC123', '23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '2019-12-11'),
('QWEEQQEQEQE', '23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '2019-12-17'),
('QWER123', '23adad46-1bb9-11ea-a1fc-d8c4971f41ea', '2019-12-11');

-- --------------------------------------------------------

--
-- Table structure for table `savings_transaction`
--

CREATE TABLE `savings_transaction` (
  `TransactionId` varchar(40) NOT NULL,
  `AccountNumber` varchar(40) NOT NULL,
  `TransactionDate` date NOT NULL,
  `Amount` decimal(10,2) NOT NULL,
  `Teller` enum('ATM','OnlineTransfer','EMP','SYSTEM') NOT NULL,
  `TransactionType` enum('Withdrawal','Deposit') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `savings_transaction`
--

INSERT INTO `savings_transaction` (`TransactionId`, `AccountNumber`, `TransactionDate`, `Amount`, `Teller`, `TransactionType`) VALUES
('0199263d-1d4d-11ea-911d-d8c4971f41ea', 'ABC123', '2019-12-13', '100.00', 'EMP', 'Deposit'),
('12da863f-1c31-11ea-a1fc-d8c4971f41ea', 'ABC123', '2019-12-09', '100.00', 'EMP', 'Withdrawal'),
('13e5bbd5-1cc5-11ea-911d-d8c4971f41ea', 'ABCD123', '2019-12-12', '100.00', 'EMP', 'Withdrawal'),
('18e478d0-1d46-11ea-911d-d8c4971f41ea', 'ABCD123', '2019-12-13', '1000.00', 'EMP', 'Withdrawal'),
('3221055e-1c36-11ea-a1fc-d8c4971f41ea', 'ABC123', '2019-12-10', '150.00', 'SYSTEM', 'Deposit'),
('3b6f4360-1c31-11ea-a1fc-d8c4971f41ea', 'ABC123', '2019-12-09', '200.00', 'EMP', 'Withdrawal'),
('42d9c0e5-20a3-11ea-b1ca-d8c4971f41ea', 'QWEEQQEQEQE', '2019-12-17', '1000.00', 'EMP', 'Deposit'),
('62798dc1-1d4d-11ea-911d-d8c4971f41ea', 'ABC123', '2019-12-13', '1000.00', 'EMP', 'Deposit'),
('6a0c0392-1c35-11ea-a1fc-d8c4971f41ea', 'ABC123', '2019-12-10', '2000.00', 'EMP', 'Deposit'),
('71c6b467-1c26-11ea-a1fc-d8c4971f41ea', 'ABC123', '2019-12-01', '100.00', 'ATM', 'Withdrawal'),
('71c9d968-1c26-11ea-a1fc-d8c4971f41ea', 'ABC123', '2019-12-02', '100.00', 'ATM', 'Withdrawal'),
('71c9da57-1c26-11ea-a1fc-d8c4971f41ea', 'ABC123', '2019-12-03', '100.00', 'ATM', 'Withdrawal'),
('7d104c41-1d4b-11ea-911d-d8c4971f41ea', 'ABC123', '2019-12-13', '13050.00', 'EMP', 'Withdrawal'),
('937d8cfa-1d48-11ea-911d-d8c4971f41ea', 'ABC123', '2019-12-13', '1000.00', 'SYSTEM', 'Deposit'),
('a220b756-1cc5-11ea-911d-d8c4971f41ea', 'ABCD123', '2019-12-12', '200.00', 'EMP', 'Withdrawal'),
('ae66343a-1d4e-11ea-911d-d8c4971f41ea', 'ABC123', '2019-12-13', '1000.00', 'EMP', 'Deposit'),
('b510131a-1d4e-11ea-911d-d8c4971f41ea', 'ABC123', '2019-12-13', '13600.00', 'EMP', 'Withdrawal'),
('bc3a7d67-1d4b-11ea-911d-d8c4971f41ea', 'ABCD123', '2019-12-13', '6340.00', 'EMP', 'Withdrawal'),
('dc2e7316-1d4c-11ea-911d-d8c4971f41ea', 'ABC123', '2019-12-13', '1500.00', 'EMP', 'Deposit'),
('de3c6d1a-1cc5-11ea-911d-d8c4971f41ea', 'ABCD123', '2019-12-12', '110.00', 'EMP', 'Withdrawal');

-- --------------------------------------------------------

--
-- Table structure for table `transfer`
--

CREATE TABLE `transfer` (
  `TransferId` varchar(40) NOT NULL,
  `TransferredFromAccountNumber` varchar(40) NOT NULL,
  `TransferredToAccountNumber` varchar(40) NOT NULL,
  `TransferAmount` decimal(10,2) NOT NULL,
  `TransferDate` date NOT NULL,
  `WithdrawId` varchar(40) NOT NULL,
  `DepositId` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `atm_withdraw`
--
ALTER TABLE `atm_withdraw`
  ADD PRIMARY KEY (`WithdrawId`),
  ADD KEY `CardNumber` (`CardNumber`);

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`BranchId`);

--
-- Indexes for table `branch_manager`
--
ALTER TABLE `branch_manager`
  ADD PRIMARY KEY (`ManagerId`),
  ADD KEY `EmployeeId` (`EmployeeId`),
  ADD KEY `BranchId` (`BranchId`);

--
-- Indexes for table `branch_managers_history`
--
ALTER TABLE `branch_managers_history`
  ADD PRIMARY KEY (`ManagerId`,`BranchId`,`JoinedDate`),
  ADD KEY `BranchId` (`BranchId`);

--
-- Indexes for table `children`
--
ALTER TABLE `children`
  ADD PRIMARY KEY (`CustomerId`),
  ADD KEY `GuardianId` (`GuardianId`);

--
-- Indexes for table `current_account`
--
ALTER TABLE `current_account`
  ADD PRIMARY KEY (`AccountNumber`),
  ADD KEY `BranchId` (`BranchId`),
  ADD KEY `CustomerId` (`CustomerId`);

--
-- Indexes for table `current_account_close`
--
ALTER TABLE `current_account_close`
  ADD PRIMARY KEY (`AccountNumber`),
  ADD KEY `CloseEmployeeId` (`CloseEmployeeId`);

--
-- Indexes for table `current_account_open`
--
ALTER TABLE `current_account_open`
  ADD PRIMARY KEY (`AccountNumber`),
  ADD KEY `OpenEmployeeId` (`OpenEmployeeId`);

--
-- Indexes for table `current_transaction`
--
ALTER TABLE `current_transaction`
  ADD PRIMARY KEY (`TransactionId`),
  ADD KEY `EmployeeId` (`EmployeeId`),
  ADD KEY `AccountNumber` (`AccountNumber`);

--
-- Indexes for table `customer_login`
--
ALTER TABLE `customer_login`
  ADD PRIMARY KEY (`CustomerId`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- Indexes for table `loan_request`
--
ALTER TABLE `loan_request`
  ADD PRIMARY KEY (`RequestId`);

--
-- Indexes for table `loan_type`
--
ALTER TABLE `loan_type`
  ADD PRIMARY KEY (`LoanTypeId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `loan_request`
--
ALTER TABLE `loan_request`
  MODIFY `RequestId` int(40) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
