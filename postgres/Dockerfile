FROM postgres:16.3

ARG pg_user=symfoititis
ARG pg_pass=student

ENV POSTGRES_USER ${pg_user}
ENV POSTGRES_PASSWORD ${pg_pass}

COPY ./pg_hba.conf /etc/pg_hba.conf
COPY ./postgresql.conf /etc/postgresql.conf
COPY ./initdb.sql /initdb.sql

CMD ["postgres", "-c", "config_file=/etc/postgresql.conf"]

EXPOSE 5432
