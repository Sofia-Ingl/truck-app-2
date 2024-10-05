create table if not exists parcel_types (
    id serial primary key,
    name text unique not null,
    shape text not null,
    symbol char not null
);
