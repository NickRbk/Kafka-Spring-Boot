#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE resourcedb;
    GRANT ALL PRIVILEGES ON DATABASE resourcedb TO nick;

    CREATE DATABASE rss_itemdb;
    GRANT ALL PRIVILEGES ON DATABASE rss_itemdb TO nick;

    \c rss_itemdb
    CREATE TABLE rss_items (
        ID serial PRIMARY KEY,
        NAME varchar(300) NOT NULL
    )
EOSQL