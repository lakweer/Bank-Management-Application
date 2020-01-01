--customer login view
CREATE VIEW  customer_login AS SELECT CustomerId, Username, Password, Status FROM customer_online_account;

CREATE VIEW  individual_online_loan_report AS
SELECT LoanId, loan.Amount AS TotalAmount, PaidSettlements, BranchId, LoanTypeName FROM loan LEFT OUTER JOIN loan_request USING(LoanId)  INNER JOIN
 loan_type ON loan_type.LoanTypeId = loan_request.LoanTypeId WHERE LoanStatus= "Late";

ALTER TABLE `loan` ADD `IssuedDate` DATE NULL AFTER `InstallmentType`;




/*INDIVIDUAL LOAN USE (1 & 2) OR (1 & 3)*/

1)CREATE VIEW all_individual_loan_details AS SELECT * FROM loan_request NATURAL LEFT JOIN loan NATURAL JOIN loan_type;

/*IF DETAILS ON ALL LOANS ARE NEEDED*/
2)CREATE VIEW individual_loan_details AS SELECT LoanTypeId,LoanTypeName, Amount, BranchId,CustomerId,EmploymentSector,EmploymentType,PaidSettlements, requestDate, LoanStatus FROM all_individual_loan_details ;

/*IF DETAILS ON ONLY APPROVED LOANS ARE NEEDED*/
3)CREATE VIEW ind_loan_details AS SELECT LoanTypeId,LoanTypeName, Amount, BranchId,CustomerId,EmploymentSector,EmploymentType,PaidSettlements, requestDate, LoanStatus FROM all_individual_loan_details WHERE ApprovedStatus="APPROVED" ;




/*ORGANIZATIONAL LOAN USE (10 & 11) OR (10 & 12)*/

10)CREATE VIEW all_organizational_loan_details AS SELECT * FROM org_loan_request NATURAL LEFT JOIN loan NATURAL JOIN loan_type;

/*IF DETAILS ON ALL LOANS ARE NEEDED*/
11)CREATE VIEW org_loan_details AS SELECT LoanTypeId,LoanTypeName, Amount, BranchId,CustomerId,PaidSettlements, requestDate, LoanStatus FROM all_organizational_loan_details ;

/*IF DETAILS ON ONLY APPROVED LOANS ARE NEEDED*/
12)CREATE VIEW org_loan_details AS SELECT LoanTypeId,LoanTypeName, Amount, BranchId,CustomerId,PaidSettlements, requestDate, LoanStatus FROM all_organizational_loan_details WHERE ApprovedStatus="APPROVED" ;





/*fOR LATECHARGES can only use 1 view*/

/*if all details are needed*/
CREATE VIEW late_charge_loan_details AS SELECT * FROM loan_installment_history NATURAL LEFT JOIN loan NATURAL JOIN loan_type WHERE LateCharges IS NOT NULL AND InstallmentDate IS NULL ;

/*if only selected details needed*/
CREATE VIEW late_charge_loan_details AS SELECT LoanTypeName,LoanId,Amount,DueDate,LateCharges,SettlementPeriod,PaidSettlements FROM loan_installment_history NATURAL LEFT JOIN loan NATURAL JOIN loan_type WHERE LateCharges IS NOT NULL AND InstallmentDate IS NULL ;