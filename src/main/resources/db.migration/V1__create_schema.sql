create table if not exists tracking(
    currency_figi varchar(12) not null,
    currency_name varchar(64) not null,
    primary key (currency_figi, currency_name)
);