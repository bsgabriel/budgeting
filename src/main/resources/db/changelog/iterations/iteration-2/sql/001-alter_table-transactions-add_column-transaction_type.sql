CREATE TYPE transaction_type AS ENUM ('INCOME', 'EXPENSE');

ALTER TABLE transactions ADD COLUMN type transaction_type;

COMMENT ON COLUMN transactions.type IS 'Defines whether the transaction is an income or an expense.';