-- Create application users
CREATE USER budgeting WITH PASSWORD 'budgeting' NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN;

-- Create liquibase users
CREATE USER budgeting_liquibase WITH PASSWORD 'budgeting_liquibase' NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN;

-- Create schemas to liquibase users
CREATE SCHEMA budgeting AUTHORIZATION budgeting_liquibase;

-- Add grant all to liquibase users
GRANT ALL ON SCHEMA budgeting TO budgeting_liquibase;

-- Add grant usage to application users
GRANT USAGE ON SCHEMA budgeting to budgeting;

-- Add grant dml operations on future tables created by liquibase user to application user
ALTER DEFAULT PRIVILEGES FOR USER budgeting_liquibase IN SCHEMA budgeting GRANT SELECT, INSERT, DELETE, UPDATE ON TABLES TO budgeting;

-- Add grant select on future sequences to application user
ALTER DEFAULT PRIVILEGES FOR USER budgeting_liquibase IN SCHEMA budgeting GRANT USAGE, SELECT ON SEQUENCES TO budgeting;

-- Add grant execute on future functions to application user
ALTER DEFAULT PRIVILEGES FOR USER budgeting_liquibase IN SCHEMA budgeting GRANT EXECUTE ON FUNCTIONS TO budgeting;