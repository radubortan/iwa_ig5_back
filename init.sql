CREATE DATABASE fast_seasonal_job_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c fast_seasonal_job_db;

CREATE TABLE employer (
    id_employer bigint not null,
    constraint cp_id_employer PRIMARY KEY (id_employer)
);

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
    id_employer bigint not null,
    constraint cp_id_job_offer PRIMARY KEY (id_job_offer),
    constraint cf_id_employer FOREIGN KEY (id_employer) REFERENCES employer(id_employer)
);

INSERT INTO employer(id_employer)VALUES (1);
INSERT INTO job_offer(id_job_offer, beginning_date, description, ending_date, num_positions, place, publishing_date, remuneration, title, id_employer)
VALUES (1,'2012-04-23T18:25:43.511Z',  'description', '2012-04-23T18:25:43.511Z', 5, 'Montpellier',  '2012-04-23T18:25:43.511Z', 2000, 'Offre initiale', 1);