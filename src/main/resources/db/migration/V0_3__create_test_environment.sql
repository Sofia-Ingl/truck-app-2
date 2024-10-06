create schema if not exists test;

create table if not exists test.parcel_types
(
    id     serial primary key,
    name   text unique not null,
    shape  text        not null,
    symbol char        not null
);
