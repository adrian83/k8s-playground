
docker:
	sudo systemctl start docker

images:
	mvn install spring-boot:build-image -DskipTests

auth-build:
	docker-compose -f docker-compose-auth-web.yml build

auth-up:
	sudo docker-compose -f docker-compose-auth-web.yml up

auth-deps:
	echo "starting postgres image (version 11.1)"
	docker rm mordeczki_auth_postgres ||:
	docker run -p 5432:5432 --env-file=auth-web/src/test/resources/db-env.db -d --name=mordeczki_auth_postgres postgres:11.1
	sleep 2
	docker cp auth-web/src/test/resources/db-schema.sql mordeczki_auth_postgres:/schema.sql
	docker exec mordeczki_auth_postgres psql mordeczki postgres -f /schema.sql

auth-run: export DB_PASSWORD=secret
auth-run: export DB_USERNAME=postgres
auth-run: export DB_URL=jdbc:postgresql://localhost:5432/mordeczki
auth-run: export DB_DRIVER=org.postgresql.Driver

auth-run: 
	echo "running auth app"
	cd auth-web && mvn clean package -DskipTests && cd ..
	java -jar auth-web/target/mordeczki-auth-web.jar


auth-deps-run:
	sudo docker-compose -f docker-compose-auth-web-deps.yml up
	


