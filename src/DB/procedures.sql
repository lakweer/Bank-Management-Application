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


/* create Individual Loan request Procedure */
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createIndividualLoanRequest`(
     IN `NICArg` CHAR(10),
    IN `branchIdArg` VARCHAR(40),
	IN `AmountArg` DECIMAL(8,2),
    IN `EmployeeIdArg` VARCHAR(40),
	IN `GrossSalaryArg` DECIMAL(10,2),
    IN `NetSalaryArg` DECIMAL(10,2),
	IN `EmploymentSectorArg` ENUM("PRIVATE", "GOV", "SELF"),
	IN `EmploymentTypeArg` ENUM("PER", "TEMP"),
	IN `ProfessionArg` VARCHAR(40),
	IN `loanTypeArg` VARCHAR(40),
    IN `requestDateArg` DATE)



    MODIFIES SQL DATA
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
    SET returnArg = "NIC is not registered!";
END IF;

SELECT returnArg;

END$$
DELIMITER ;



/* create Organization Loan request Procedure */
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createOrgLoanRequest`(
    IN `RegisterIdArg` VARCHAR(40),
	IN `BranchIdArg` VARCHAR(40),
	IN `AmountArg` DECIMAL(8,2),
    IN `EmployeeIdArg` VARCHAR(40),
	IN `ProjectGrossValueArg` DECIMAL(10,2),
	IN `LoanReasonArg` ENUM("Construction", "Asset_Purchase","Refinancing","Other_Reason"),
	IN `OrganizationTypeArg` ENUM("Individual", "Joint", "Pvt_Ltd_Co", "Limited_Co", "Trust", "Other"),
	IN `requestDateArg` DATE)


    MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE CustomerIdArg VARCHAR(40);
DECLARE returnArg VARCHAR(100);


/* Set Default Statement */
SET returnArg = "Something Went Wrong while Creating a Loan Request!";

/* check the customer is already registred */
SELECT `CustomerId` INTO CustomerIdArg FROM `organization` WHERE `RegisterNumber`= RegisterIdArg;

/* check for the existance of the customer */
IF LENGTH(CustomerIdArg) > 0 THEN

    /* Add the data of loan request to the org_loan_request Table */

    INSERT INTO `org_loan_request`(`CustomerId`, `BranchId`, `Amount`, `EmployeeId`, `ProjectGrossValue`, `LoanReason`, `OrganizationType`, `requestDate`)
    VALUES (CustomerIdArg, BranchIdArg, AmountArg, EmployeeIdArg, ProjectGrossValueArg, LoanReasonArg, OrganizationTypeArg, requestDateArg );

    SET returnArg = "Success";
    COMMIT;

ELSE
    SET returnArg = "Registration number does not exists!";
END IF;

SELECT returnArg;

END$$
DELIMITER ;

/*current account open procedure */
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

/*current account close procedure */
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

/*current account transaction procedure */
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `currentAccountTransaction`(IN `TransactionEmployeeIdArg` VARCHAR(40), IN `currentAccountNumberArg` VARCHAR(40), IN `TransactionDateArg` DATE, IN `AmountArg` DECIMAL(10,2), IN `chequeNumberArg` VARCHAR(40), IN `TransactionType` ENUM("Withdrawal","Deposit"))
    MODIFIES SQL DATA
BEGIN

/* DECLARE VARIABLES */
DECLARE accountNumberArg VARCHAR(40);
DECLARE balanceArg DECIMAL(12,2);
DECLARE statusArg ENUM('0','1');
DECLARE returnArg VARCHAR(255);

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
		INSERT INTO current_transaction (EmployeeId, AccountNumber, TransactionDate, Amount, ChequeNumber, TransactionType) VALUES
    	(TransactionEmployeeIdArg, currentAccountNumberArg, TransactionDateArg, AmountArg, chequeNumberArg, TransactionType);

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