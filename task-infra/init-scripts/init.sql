DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'taskuser') THEN
    EXECUTE format('CREATE USER taskuser WITH PASSWORD %L CREATEDB', current_setting('POSTGRES_PASSWORD'));
END IF;
END
$$;

ALTER USER taskuser WITH SUPERUSER CREATEDB CREATEROLE;

CREATE DATABASE postgres;

GRANT ALL PRIVILEGES ON DATABASE postgres TO taskuser;