@echo OFF

call mvn clean install package -DskipTests
copy target\sin.war docker\resources\sin.war /Y
call docker-compose up -d sin-db
call docker-compose up --build sin-wildfly