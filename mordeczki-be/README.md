# Mordeczki 

## Build docker images:
```
mvn clean install
mvn spring-boot:build-image
```

## Start all the dependencies needed by auth-api module
```
docker-compose -f docker-compose-auth-web-deps.yml up
```

## Run auth-api locally
```
cd auth-api && ./start-local.sh
```

