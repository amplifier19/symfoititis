#!/bin/bash
set -e 

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname="$POSTGRES_DB" <<-EOSQL
        CREATE USER ${SERVICE_USER} WITH PASSWORD '${SERVICE_PASSWORD}';
        GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE universities TO ${SERVICE_USER};
        GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE departments TO ${SERVICE_USER};
EOSQL
