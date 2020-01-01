--customer login view
CREATE VIEW  customer_login AS SELECT CustomerId, Username, Password, Status FROM customer_online_account;

CREATE VIEW  individual_online_loan_report AS
SELECT LoanId, loan.Amount AS TotalAmount, PaidSettlements, BranchId, LoanTypeName FROM loan LEFT OUTER JOIN loan_request USING(LoanId)  INNER JOIN
 loan_type ON loan_type.LoanTypeId = loan_request.LoanTypeId WHERE LoanStatus= "Late";

ALTER TABLE `loan` ADD `IssuedDate` DATE NULL AFTER `InstallmentType`;