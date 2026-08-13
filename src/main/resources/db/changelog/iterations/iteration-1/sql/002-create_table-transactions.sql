create table transactions (
    transaction_id SERIAL,
    description varchar(255) not null,
    amount NUMERIC(12, 2) NOT NULL,
    category_id integer not null,
    constraint pk_transaction primary key (transaction_id),
    constraint fk_transaction_category foreign key (category_id) references categories (category_id)
);

comment on table transactions is 'Stores individual financial transactions, including their amounts and categorizations.';
comment on column transactions.transaction_id is 'Unique identifier for the financial transaction. Auto-incremented primary key.';
comment on column transactions.description is 'Details or narrative describing the transaction.';
comment on column transactions.amount is 'Monetary value of the transaction. Uses precision 19 and scale 4 to prevent rounding errors.';
comment on column transactions.category_id is 'Foreign key referencing the category this transaction belongs to.';
