FROM postgres:16.3

COPY ./postgresql.conf /etc/postgresql.conf
COPY ./pg_hba.conf /etc/pg_hba.conf
COPY ./init-db.sql /docker-entrypoint-initdb.d/init-db.sql
COPY ./init-user.sh /docker-entrypoint-initdb.d/init-user.sh

EXPOSE 5432
