CREATE DATABASE fast_seasonal_job_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c fast_seasonal_job_db;

CREATE TABLE job_offer (
    id_job_offer bigint not null ,
    title varchar(50) not null,
    description varchar(500) not null,
    beginning_date date not null ,
    ending_date date not null ,
    place varchar(100) not null ,
    num_positions int not null ,
    remuneration float not null ,
    publishing_date date not null,
    constraint cp_id_job_offer PRIMARY KEY (id_job_offer)
);