# Mordeczki 

## Run `migration` with docker compose:
1. Start docker: `make docker`
2. Build docker images: `mvn install spring-boot:build-image -DskipTests`
3. Start dependencies and application: `docker-compose -f docker-compose-migration.yml up`


## Run `auth-web` locally:
1. Start docker: `make docker`
2. Build docker images: `mvn install spring-boot:build-image -DskipTests`
3. Start dependencies (postgres, kafka...): `make auth-deps-run`
4. Start application: `cd auth-web && ./start-local.sh`

## Run `auth-web` with docker compose:
1. Start docker: `make docker`
2. Build docker images: `mvn install spring-boot:build-image -DskipTests`
3. Start dependencies and application: `docker-compose -f docker-compose-auth-web.yml up`


