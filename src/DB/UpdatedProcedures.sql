/*add fixed deposit interest*/
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addFixedDepositInterest`()
    MODIFIES SQL DATA
BEGIN
	UPDATE fixed_deposit_account INNER JOIN fixed_deposit_type USING (FDType) INNER JOIN savings_account
	ON savings_account.AccountNumber = fixed_deposit_account.SavingsAccountNumber
	SET savings_account.Balance = savings_account.Balance + fixed_deposit_account.DepositAmount*fixed_deposit_type.InterestRate*30/365
	WHERE fixed_deposit_account.MaturityDate > CURDATE() AND MOD( DATEDIFF(CURDATE(), fixed_deposit_account.DepositDate) , 30 )=0;

    INSERT INTO savings_transaction(TransactionId, AccountNumber,TransactionDate,Amount,Teller,TransactionType)
    SELECT UUID(), savings_account.AccountNumber, CURDATE(), fixed_deposit_account.DepositAmount*fixed_deposit_type.InterestRate*30/365, "SYSTEM", "Deposit"
    FROM fixed_deposit_account INNER JOIN fixed_deposit_type USING (FDType) INNER JOIN savings_account
    ON savings_account.AccountNumber = fixed_deposit_account.SavingsAccountNumber
    WHERE fixed_deposit_account.MaturityDate > CURDATE() AND MOD( DATEDIFF(CURDATE(), fixed_deposit_account.DepositDate) , 30 )= 0;


END$$
DELIMITER ;









/* add new Employee Procedure */
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewEmployee`(
    IN `firstNameArg` VARCHAR(50),
	IN `lastNameArg` VARCHAR(50),
    IN `branchIdArg` VARCHAR(40),
    IN `nicArg` CHAR(10),
    IN `emailArg` VARCHAR(250),
    IN `dobArg` DATE,
    IN `empTypeArg` ENUM("Normal", "BranchManager"),
    IN `usernameArg` VARCHAR(50),
    IN `passwordArg` VARCHAR(500))
    MODIFIES SQL DATA
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
DELIMITER ;





/*add online customer*/
DELIMITER $$
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
DELIMITER ;






/*cancel debit card*/
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `cancelDebitCard`(IN `SavingsAccountNumberArg` VARCHAR(40))
    MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE StatusArg enum('0', '1');
DECLARE ActiveDebit enum('0', '1');
DECLARE returnArg VARCHAR(255);

/* Set Default Statement */
SET returnArg = "Something Went Wrong During cancelling debit card!";

/*select the status of the savings account*/
SELECT Status INTO StatusArg FROM savings_account WHERE AccountNumber = SavingsAccountNumberArg;

/*check whether a valid card is there to cancel*/
SELECT COUNT(STATUS) INTO ActiveDebit FROM debit_card WHERE AccountNumber = SavingsAccountNumberArg AND STATUS = "1";

/* check whether the savings account is active */
IF StatusArg = "1" THEN

	/*check whether an active debit card is there to cancel*/
    	IF ActiveDebit = 1 THEN

			UPDATE debit_card SET Status = "0" WHERE AccountNumber = SavingsAccountNumberArg;
    		SET returnArg = "Success";

        ELSE
        	SET returnArg = "no active debit card to cancel";
        END IF;

ELSE
     SET returnArg = "not an active savings account";
END IF;
SELECT returnArg;
END$$
DELIMITER ;









/*create individual loan request*/
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createIndividualLoanRequest` (IN `NICArg` CHAR(10), IN `branchIdArg` VARCHAR(40), IN `AmountArg` DECIMAL(8,2), IN `EmployeeIdArg` VARCHAR(40), IN `GrossSalaryArg` DECIMAL(10,2), IN `NetSalaryArg` DECIMAL(10,2), IN `EmploymentSectorArg` ENUM("PRIVATE","GOV","SELF"), IN `EmploymentTypeArg` ENUM("PER","TEMP"), IN `ProfessionArg` VARCHAR(40), IN `loanTypeArg` VARCHAR(40),IN `settlementPeriodArg` INT(3),IN `noOfSettlementsArg` INT(3), IN `requestDateArg` DATE)  MODIFIES SQL DATA
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
    INSERT INTO `loan_request`(`CustomerId`, `BranchId`, `Amount`, `EmployeeId`, `GrossSalary`, `NetSalary`, `EmploymentSector`, `EmploymentType`, `Profession`, `LoanTypeId`,`SettlementPeriod`, `NoOfSettlements`, `requestDate`)
    VALUES (CustomerIdArg, branchIdArg, AmountArg, EmployeeIdArg, GrossSalaryArg, NetSalaryArg, EmploymentSectorArg, EmploymentTypeArg, ProfessionArg, LoanTypeIdArg,settlementPeriodArg, noOfSettlementsArg , requestDateArg );

    SET returnArg = "Success";
    COMMIT;

ELSE
    SET returnArg = "NIC is not registered!";
END IF;

SELECT returnArg;

END$$
DELIMITER ;






/*create organization loan request*/
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createOrgLoanRequest` (IN `RegisterIdArg` VARCHAR(40), IN `BranchIdArg` VARCHAR(40), IN `AmountArg` DECIMAL(8,2), IN `EmployeeIdArg` VARCHAR(40), IN `ProjectGrossValueArg` DECIMAL(12,2), IN `LoanReasonArg` VARCHAR(40), IN `OrganizationTypeArg` ENUM("Individual","Joint","Pvt_Ltd_Co","Limited_Co","Trust","Other"),IN `settlementPeriodArg` INT(3),IN `noOfSettlementsArg` INT(3), IN `requestDateArg` DATE)  MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE CustomerIdArg VARCHAR(40);
DECLARE returnArg VARCHAR(100);
DECLARE LoanTypeIdArg VARCHAR(10);


/* Set Default Statement */
SET returnArg = "Something Went Wrong while Creating a Loan Request!";

/* check the customer is already registred */
SELECT `CustomerId` INTO CustomerIdArg FROM `organization` WHERE `RegisterNumber`= RegisterIdArg;

/* check for the existance of the customer */
IF LENGTH(CustomerIdArg) > 0 THEN

    /* get the loan type id from loan_type table */
    	SELECT `LoanTypeId` INTO LoanTypeIdArg FROM `loan_type` WHERE `LoanTypeName`= LoanReasonArg;

    /* Add the data of loan request to the org_loan_request Table */

    INSERT INTO `org_loan_request`(`CustomerId`, `BranchId`, `Amount`, `EmployeeId`, `ProjectGrossValue`, `LoanTypeId`, `OrganizationType`,`SettlementPeriod`, `NoOfSettlements`, `requestDate`)
    VALUES (CustomerIdArg, BranchIdArg, AmountArg, EmployeeIdArg, ProjectGrossValueArg, LoanTypeIdArg, OrganizationTypeArg,settlementPeriodArg, noOfSettlementsArg , requestDateArg );

    SET returnArg = "Success";
    COMMIT;

ELSE
    SET returnArg = "Register Number Doesn't Exist";
END IF;

SELECT returnArg;

END$$
DELIMITER ;





/*current account close*/
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `currentAccountClose`(IN `currentAccountNumberArg` VARCHAR(40), IN `CurrentAccountCloseDateArg` DATE, IN `CloseEmployeeIdArg` VARCHAR(40))
    MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE accountNumberArg VARCHAR(40);
DECLARE statusArg VARCHAR(40);
DECLARE balanceArg DECIMAL(12,2);
DECLARE returnArg VARCHAR(255);

/* BEGIN TRANSACTION */
START TRANSACTION;

/* Set Default Statement */
SET returnArg = "Something Went Wrong During Current Account Close!";

/* select the accountNumber, status, and balance from current_account table */
SELECT AccountNumber INTO accountNumberArg FROM current_account WHERE AccountNumber = currentAccountNumberArg;
SELECT Status INTO statusArg FROM current_account WHERE AccountNumber = currentAccountNumberArg;
SELECT Balance INTO balanceArg FROM current_account WHERE AccountNumber = currentAccountNumberArg;

/* check the existence of the account */
IF LENGTH(accountNumberArg) > 1 THEN

	/*check whether the account is active */
	IF statusArg = 1 THEN

		/*check whether payment is due */
		IF balanceArg < 0 THEN
        	SET returnArg = "Can't close. amount due";

        ELSE

        	/* insert the data into transaction table */
            INSERT INTO `current_transaction`(`EmployeeId`, `AccountNumber`, `TransactionDate`, `Amount`,`TransactionType`)
            VALUES (CloseEmployeeIdArg, currentAccountNumberArg, CurrentAccountCloseDateArg, balanceArg, "Withdrawal");

        	/*set status and balance to 0*/
        	Update current_account SET `Status` = "0", `Balance` = 0.00 WHERE  AccountNumber = currentAccountNumberArg;

    		/* insert the values into current account open table */
    		INSERT INTO current_account_close (AccountNumber, CloseEmployeeId, CloseDate) VALUES (currentAccountNumberArg, CloseEmployeeIdArg, CurrentAccountCloseDateArg);

     		SET returnArg = "Account successfully closed. Balance withdrawed.";
     		COMMIT;
        END IF;

     ELSE
     	SET returnArg = "Account already closed";
     END IF;
ELSE
     SET returnArg = "Account doesn't exist";
END IF;
SELECT returnArg;
END$$
DELIMITER ;







/*current account open*/
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `currentAccountOpen`(IN `currentAccountNumberArg` VARCHAR(40), IN `customerTempIdArg` VARCHAR(40), IN `CurrentAccountOpenDateArg` DATE, IN `CustomerTypeArg` VARCHAR(40), IN `OpenEmployeeIdArg` VARCHAR(40), IN `BranchIdArg` VARCHAR(40))
    MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE customerIdArg VARCHAR(40);
DECLARE returnArg VARCHAR(255);

/* BEGIN TRANSACTION */
START TRANSACTION;

/* Set Default Statement */
SET returnArg = "Something Went Wrong During Current Account Open!";

/* select the customerId from the respective table based on customer type */
IF CustomerTypeArg = "Individual" THEN
 	SELECT CustomerId INTO customerIdArg FROM individual WHERE Nic = customerTempIdArg;
ELSE
    SELECT CustomerId INTO customerIdArg FROM organization WHERE RegisterNumber = customerTempIdArg;
END IF;

/* check the existance of the customer */
IF LENGTH(customerIdArg) > 1 THEN

	/* insert the values into current account table */
	INSERT INTO current_account (AccountNumber, BranchId, CustomerId, Balance, Status) VALUES
        (currentAccountNumberArg, BranchIdArg, customerIdArg, 0, "1");

    /* insert the values into current account open table */
    INSERT INTO current_account_open (AccountNumber, OpenEmployeeId, OpenDate) VALUES
    	(currentAccountNumberArg, OpenEmployeeIdArg, CurrentAccountOpenDateArg);

     SET returnArg = "Success";
     COMMIT;
ELSE
     SET returnArg = "Customer doesn't exist";
END IF;
SELECT returnArg;
END$$
DELIMITER ;






/*current account transaction*/
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `currentAccountTransaction`(IN `TransactionEmployeeIdArg` VARCHAR(40), IN `currentAccountNumberArg` VARCHAR(40), IN `TransactionDateArg` DATE, IN `AmountArg` DECIMAL(10,2), IN `chequeNumberArg` VARCHAR(40), IN `TransactionType` ENUM("Withdrawal","Deposit"), IN `transactionMode` ENUM("Cash","Cheque"))
    MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE accountNumberArg VARCHAR(40);
DECLARE balanceArg DECIMAL(12,2);
DECLARE statusArg ENUM('0','1');
DECLARE returnArg VARCHAR(255);
DECLARE transactionIdArg int(40);

/* BEGIN TRANSACTION */
START TRANSACTION;

/* Set Default Statement */
SET returnArg = "Something Went Wrong During transaction!";

/* select the account number */
SELECT AccountNumber INTO accountNumberArg FROM current_account WHERE AccountNumber = currentAccountNumberArg;
SELECT Balance INTO balanceArg FROM current_account WHERE AccountNumber = currentAccountNumberArg;
SELECT Status INTO statusArg FROM current_account WHERE AccountNumber = currentAccountNumberArg;

/*check the existence of the account*/
IF LENGTH(accountNumberArg) > 1 THEN

	/*check whether account is activated*/
    IF statusArg = '1' THEN

		/* insert the values into current transaction table */
		INSERT INTO current_transaction (EmployeeId, AccountNumber, TransactionDate, Amount, TransactionMode, TransactionType) VALUES
    	(TransactionEmployeeIdArg, currentAccountNumberArg, TransactionDateArg, AmountArg, transactionMode, TransactionType);
        SELECT TransactionId INTO transactionIdArg FROM current_transaction WHERE TransactionId = LAST_INSERT_ID();

        IF transactionMode = "cheque" THEN
        	INSERT INTO current_cheque (TransactionId, chequeNumber) VALUES (transactionIdArg, chequeNumberArg);

        END IF;

        /*to check whether the transaction is a withdrawal*/
    	IF TransactionType = "Withdrawal" THEN

    		/*substract the withdrawed amount from the balance*/
    		Update current_account SET `Balance` = balanceArg - AmountArg WHERE  AccountNumber = currentAccountNumberArg;
        	SET returnArg = "Success";
    		COMMIT;

    	ELSE
    		/*add the deposited amount to the balance*/
    		Update current_account SET `Balance` = balanceArg + AmountArg WHERE  AccountNumber = currentAccountNumberArg;
        	SET returnArg = "Success";
    		COMMIT;
    	END IF;

    ELSE
    	SET returnArg = "Account closed";
    END IF;

ELSE
    SET returnArg = "Customer does not exist";
END IF;
SELECT returnArg;
END$$
DELIMITER ;







/* Fixed Deposit Account Open Procedure */
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `fixedDepositOpen`(
    IN `fdAccountNumberArg` VARCHAR(40),
	IN `savingsAccountNumberArg` VARCHAR(40),
    IN `customerNICArg` CHAR(10),
    IN `depositAmountArg` DECIMAL(12,2),
    IN `depositDateArg` DATE,
    IN `matuarityDateArg` DATE,
	IN `fdTypeArg` VARCHAR(10))
    MODIFIES SQL DATA
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
DELIMITER ;







/*issue new debit card*/
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `issueNewDebitCard`(IN `CardNumberArg` CHAR(16), IN `PinNumberArg` CHAR(4), IN `SavingsAccountNumberArg` VARCHAR(40), IN `IssuedDateArg` DATE, IN `ExpiryDate` DATE)
    MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE StatusArg enum('0', '1');
DECLARE ActiveDebit enum('0', '1');
DECLARE returnArg VARCHAR(255);

/* BEGIN TRANSACTION */
START TRANSACTION;

/* Set Default Statement */
SET returnArg = "Something Went Wrong During issuing debit card!";

/*select the status of the savings account*/
SELECT Status INTO StatusArg FROM savings_account WHERE AccountNumber = SavingsAccountNumberArg;

/* check whether the savings account is active */
IF StatusArg = "1" THEN

	/*check whether a card is already issued for the savings account*/
    SELECT COUNT(STATUS) INTO ActiveDebit FROM debit_card WHERE AccountNumber = SavingsAccountNumberArg AND STATUS = "1";
    IF ActiveDebit = 1 THEN

    	SET returnArg = "Debit card already exists for the savings account";

	ELSE
		/* insert the values into debit card table */
		INSERT INTO debit_card (CardNumber, PinNumber, AccountNumber, IssuedDate, ExpiryDate, Status) VALUES
        	(CardNumberArg, PinNumberArg, SavingsAccountNumberArg, IssuedDateArg, ExpiryDate, "1");

     	SET returnArg = "Success";
     	COMMIT;

   	END IF;
ELSE
     SET returnArg = "Not a valid savings account";
END IF;
SELECT returnArg;
END$$
DELIMITER ;








/*open child savings account procedure*/
DELIMITER $$
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
DELIMITER ;




/*remove normal employee procedure*/
DELIMITER $$
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
DELIMITER ;






/* Savings Account Closing Procedure */
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `savingsClose`(
    IN `accountNumberArg` VARCHAR(40),
    IN `dateOfClosingArg` DATE,
	IN `empId` VARCHAR(40))
    MODIFIES SQL DATA
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
DELIMITER ;







/* Savings Withdrawal PROCEDURE */

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `savingsWithdraw`(
    IN `accountNumberArg` VARCHAR(40),
    IN `withdrawAmountArg` DECIMAL(10,2),
    IN `dateOfWithdrawalArg` DATE,
    IN `tellerArg` VARCHAR(25),
	IN `empId` VARCHAR(40))
    MODIFIES SQL DATA
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
DELIMITER ;








/* Savings Deposit Procedure */
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `savingsDeposit`(
    IN `accountNumberArg` VARCHAR(40),
    IN `withdrawAmountArg` DECIMAL(10,2),
    IN `dateOfWithdrawalArg` DATE,
    IN `tellerArg` VARCHAR(25),
	IN `empId` VARCHAR(40))
    MODIFIES SQL DATA
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
DELIMITER ;






/*view current employees procedure*/
DELIMITER $$
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
DELIMITER ;


/*check status of loan request*/

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkLoanStatus`(
    IN `LoanTypeArg` ENUM("Individual", "Organizational"),
	IN `Request_Id` INT(40))


    MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE returnArg VARCHAR(100);
DECLARE loanArg INT(40);


/* Set Default Statement */
SET returnArg = "Something Went Wrong while Creating a Loan Request!";

/* Check loan type */
IF LoanTypeArg = "Individual" THEN

	/* check the loan request is already requested */
	SELECT `RequestId` INTO loanArg FROM `loan_request` WHERE `RequestId`= Request_Id;

	/* check for the existence of the loan request */
	IF LENGTH(loanArg) > 0 THEN

		/* get the status of the request from loan_request table */
		SELECT `ApprovedStatus` INTO returnArg FROM `loan_request` WHERE `RequestId`= Request_Id;

		COMMIT;

	ELSE
		SET returnArg = "Request Id does not exist! Plz check and try again";
    END IF;

ELSE
	/* check the loan request is already requested */
	SELECT `RequestId` INTO loanArg FROM `org_loan_request` WHERE `RequestId`= Request_Id;

	/* check for the existence of the loan request */
	IF LENGTH(loanArg) > 0 THEN

		/* get the status of the request from org_loan_request table */
		SELECT `ApprovedStatus` INTO returnArg FROM `org_loan_request` WHERE `RequestId`= Request_Id;

		COMMIT;

	ELSE
		SET returnArg = "Request Id does not exist! Plz check and try again";
	END IF;

END IF;

SELECT returnArg;

END$$
DELIMITER ;



/*update Loan Settlements*/

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateSettlements`(

	IN `Loan_Id` VARCHAR(40),
	IN `DateArg` DATE,
	IN `SettleAmountArg` DECIMAL(8,2))


    MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE returnArg VARCHAR(100);
DECLARE loanArg VARCHAR(40);
DECLARE InstallmentIdArg INT(5);
DECLARE DueDateArg DATE;
DECLARE LoanAmountArg DECIMAL(8,2);
DECLARE NoOfSettlementsArg INT(3);
DECLARE InstallmentArg DECIMAL(8,2);
DECLARE LateAmountArg DECIMAL(8,2);
DECLARE LateInstallmentArg DECIMAL(8,2);



/* Set Default Statement */
SET returnArg = "Something Went Wrong while Settling loan!";

/* check the loan is already approved */
SELECT `LoanId` INTO loanArg FROM `loan` WHERE `LoanId`= Loan_Id;
SELECT `Amount` INTO LoanAmountArg FROM `loan` WHERE `LoanId`= Loan_Id;
SELECT `NoOfSettlements` INTO NoOfSettlementsArg FROM `loan` WHERE `LoanId`= Loan_Id;
SET InstallmentArg = LoanAmountArg/NoOfSettlementsArg;

		/* check for the existence of the loan */
		IF LENGTH(loanArg) > 0 THEN

			/**/
			SELECT `InstallmentId` INTO InstallmentIdArg FROM loan_installment_history WHERE `LoanId`= Loan_Id AND InstallmentDate IS NULL ORDER BY DueDate ASC LIMIT 1;
			SELECT `DueDate` INTO DueDateArg FROM loan_installment_history WHERE `LoanId`= Loan_Id AND InstallmentDate IS NULL ORDER BY DueDate ASC LIMIT 1;
			SELECT `LateCharges` INTO LateAmountArg FROM loan_installment_history WHERE `LoanId`= Loan_Id AND InstallmentDate IS NULL ORDER BY DueDate ASC LIMIT 1;

			IF LENGTH(InstallmentIdArg) > 0 THEN
				/*CHECK FOR LATE CHARGES*/
				IF DueDateArg>DateArg THEN

					IF InstallmentArg=SettleAmountArg THEN

						/* Add the data of loan request to the loan_request Table */
						UPDATE `loan_installment_history` SET `InstallmentDate`=DateArg, `Amount`=SettleAmountArg WHERE InstallmentId=InstallmentIdArg;
						UPDATE `loan` SET `PaidSettlements`= PaidSettlements + 1 WHERE `LoanId`= Loan_Id;

						SET returnArg = "Success";
						COMMIT;
					ELSE
						SET returnArg = CONCAT("Check installment ammount. Your payment should be   ",InstallmentArg, ". There are no late charges.") ;
					END IF;
				ELSE
					SET LateInstallmentArg= InstallmentArg + LateAmountArg ;
					IF LateInstallmentArg=SettleAmountArg THEN

						/* Add the data of loan request to the loan_request Table */
						UPDATE `loan_installment_history` SET `InstallmentDate`=DateArg, `Amount`=SettleAmountArg WHERE InstallmentId=InstallmentIdArg;
                        UPDATE `loan` SET `PaidSettlements`= PaidSettlements + 1 WHERE `LoanId`= Loan_Id;
						SET returnArg = "Success";
						COMMIT;
					ELSE
						SET returnArg = CONCAT("Due date has expierd. You have late charge of ",LateAmountArg, ". Your payment should be  ", LateInstallmentArg, ", with late charges") ;
					END IF;

				END IF;
			ELSE
				SET returnArg = "Your Installments are settled up to date";
			END IF;


		ELSE
			SET returnArg = "Loan ID does not exist! Plz check and try again";
		END IF;


SELECT returnArg;


END$$
DELIMITER ;












