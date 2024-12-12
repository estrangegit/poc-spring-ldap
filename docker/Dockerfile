FROM postgres:16.2-alpine

ENV POSTGRES_DB pocdb
ENV POSTGRES_USER pocdb
ENV POSTGRES_PASSWORD 123456

COPY init_db.sql /docker-entrypoint-initdb.d/

COPY init_docker.sh /
RUN sh /init_docker.sh
