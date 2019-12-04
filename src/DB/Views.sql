--customer login view
CREATE VIEW  customer_login AS SELECT CustomerId, Username, Password, Status FROM customer_online_account;