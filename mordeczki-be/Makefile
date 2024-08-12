
docker:
	sudo systemctl start docker

images:
	mvn install spring-boot:build-image -DskipTests

compose-up:
	docker-compose -f docker-compose.yml up --build

auth-build:
	docker-compose -f docker-compose-auth-api.yml build

auth-up:
	sudo docker-compose -f docker-compose-auth-api.yml up

auth-deps:
	echo "starting postgres image (version 11.1)"
	docker rm mordeczki_auth_postgres ||:
	docker run -p 5432:5432 --env-file=auth-api/src/test/resources/db-env.db -d --name=mordeczki_auth_postgres postgres:11.1
	sleep 2
	docker cp auth-api/src/test/resources/db-schema.sql mordeczki_auth_postgres:/schema.sql
	docker exec mordeczki_auth_postgres psql mordeczki postgres -f /schema.sql
	docker run -d -t -i -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 confluentinc/cp-zookeeper:6.0.9
	docker run -d -t -i -e KAFKA_ZOOKEEPER_CONNECT='zookeeper:2181' -e KAFKA_ADVERTISED_LISTENERS='PLAINTEXT://localhost:9092,PLAINTEXT_HOST://localhost:29092' -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP='PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT' -e KAFKA_INTER_BROKER_LISTENER_NAME=PLAINTEXT -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -p 29092:29092 confluentinc/cp-kafka:6.2.6

auth-run: export DB_PASSWORD=secret
auth-run: export DB_USERNAME=postgres
auth-run: export DB_URL=jdbc:postgresql://localhost:5432/mordeczki
auth-run: export DB_DRIVER=org.postgresql.Driver
auth-run: export KAFKA_ADDRESS=localhost:29092

auth-run: 
	echo "running auth app"
	cd auth-api && mvn clean package -DskipTests && cd ..
	java -jar auth-api/target/mordeczki-auth-api.jar


auth-deps-run:
	sudo docker-compose -f docker-compose-auth-api-deps.yml up
	


