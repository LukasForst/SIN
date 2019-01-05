create database docker_db;
create user docker_user with encrypted password 'docker';
grant all privileges on database docker_db to docker_user;
