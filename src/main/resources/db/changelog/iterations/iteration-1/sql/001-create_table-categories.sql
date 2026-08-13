create table categories (
    category_id serial,
    description varchar(255) not null,
    constraint pk_category primary key (category_id),
    constraint uq_category_description unique (description)
);

comment on table categories is 'Stores the financial categories used to classify transactions.';
comment on column categories.category_id is 'Unique identifier for the category. Auto-incremented primary key.';
comment on column categories.description is 'Name or description of the financial category (e.g., Food, Salary, Utilities).';
