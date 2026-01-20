create table beer_consumption
(
    id          SERIAL PRIMARY KEY ,
    user_id     varchar(80) not null,
    consumed_at timestamp   not null
);
