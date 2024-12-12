# POC SPRING LDAP
The goal of this POC is to configure security layer to retrieve users from an LDAP server instead of an internal database. 

## Necessary tools to build and launch POC SPRING LDAP project

- [JDK 21.0.2](https://jdk.java.net/21/)
- [Maven 3](https://maven.apache.org)
- [Docker Desktop](https://docs.docker.com/get-started/overview/)
- [Node.js v22.12.0(LTS)](https://nodejs.org/en/download)

## LDAP server and PostgreSQL database management

- A LDAP server, LDAP GUI and a PostgreSQL database can be launched with the following files: ``./docker/docker-compose.yaml``, ``./docker/ldifs/init-ldap.ldif``
- Create the containers using the following command line executed from the ``./docker`` folder: ``docker-compose up -d``
- An LDAP server is accessible at http://localhost:389 and a GUI at http://localhost:8081 
- Access the LDAP server container with the command: ``docker exec -it --user root ldap-server /bin/bash``
- List agents registered in the LDAP server: ``ldapsearch -x -H ldap://localhost:389 -D "cn=admin,dc=example,dc=com" -w "adminpassword" -b "dc=example,dc=com" "(objectClass=person)"``
- Access the PostgreSQL database using the following command: ``psql --username=pocdb  --dbname=pocdb``
- You can initialize ``t_poc_vhl_vehicle`` table with ``./docker/data/t_poc_vhl_vehicle.csv`` content: ``\COPY poc.t_poc_vhl_vehicle from '/absolute/path/t_poc_vhl_vehicle.csv' with (null 'NULL', format CSV, ENCODING 'UTF-8');``
- Delete the containers using the following command line executed from the ``./docker`` folder: ``docker-compose down``

## A series of command lines to use to launch POC SPRING LDAP application
### Package and launch back-end application
Execute the following command lines in project folder:
- ``mvn clean package``
- ``java -jar ./target/jar_file_name.jar``
### launch front-end application
Execute the following command lines in ``./src/main/js/front_end``:
- ``npm install``
- ``npm run start``
### Package and launch both back-end and front-end application
- ``mvn clean package -P prod``
- ``java -jar ./target/jar_file_name.jar``
