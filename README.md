# CODE WITH QUARKUS

## DATABASE (MYSQL)
> sm-bff > main > resources > script > mysql

> generate id use "strategy = GenerationType.SEQUENCE"

## BUILD
> mvn clean install

## RUN IN DEV
> mvn run dev

## RUN IN PROD
> java -jar target/quarkus-app/quarkus-run.jar --moduleName=bff > bff.log 2>&1 & disown