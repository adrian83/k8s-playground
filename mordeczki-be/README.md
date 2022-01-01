


### Running Auth 
1. `make docker`
2. `make auth-deps`
3. `make auth-run`



### Build everything and create docker images: `mvn install spring-boot:build-image -DskipTests`

docker-compose -f docker-compose-auth-web.yml up
docker-compose -f docker-compose-migration.yml up