CREATE DATABASE fsj
    WITH
    OWNER = fsj
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c fsj;

CREATE TABLE CV (
	jobseeker_id integer,
	name_file_cv varchar(100),
	keywords_cv varchar[]
);

