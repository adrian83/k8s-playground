# Mordeczki 

## Build docker images:
```
mvn clean install spring-boot:build-image
```

## Start all the dependencies needed by app-auth-api module
```
docker-compose -f docker-compose-auth-web-deps.yml up
```

## Run app-auth-api locally
```
cd app-auth-api && ./start-local.sh
```

