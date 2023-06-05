create table if not exists customer
(
    id          bigserial,
    create_date timestamp not null,
    update_date timestamp,
    first_name  varchar(255),
    last_name   varchar(255),
    email       varchar(255),
    constraint customer_pkey_id primary key (id)
);
