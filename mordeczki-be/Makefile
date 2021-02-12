
docker:
	sudo systemctl start docker

user-build:
	docker-compose -f docker-compose-user.yml build

user-up:
	sudo docker-compose up -f docker-compose-user.yml

user-deps:
	echo "starting postgres image (version 11.1)"
	docker rm mordeczki_user_postgres ||:
	docker run -p 5432:5432 --env-file=user/src/test/resources/db-env.db -d --name=mordeczki_user_postgres postgres:11.1
	sleep 2
	docker cp user/src/test/resources/db-schema.sql mordeczki_user_postgres:/schema.sql
	docker exec mordeczki_user_postgres psql mordeczki postgres -f /schema.sql

user-run: export DB_PASSWORD=secret
user-run: export DB_USERNAME=postgres
user-run: export DB_URL=jdbc:postgresql://localhost:5432/mordeczki
user-run: export DB_DRIVER=org.postgresql.Driver

user-run: 
	echo "running user app"
	cd user && mvn clean package -DskipTests && cd ..
	java -jar user/target/mordeczki-user.jar



fe-format:
	cd quotes-fe && dartfmt -w -l 160 --fix .

fe-get:
	echo "getting frontend dependencies" 
	cd quotes-fe && pub upgrade && pub get 

fe-build:
	echo "building frontend"
	#cd quotes-fe && pub run build_runner clean
	rm quotes-fe/lib/generated_consts.dart ||:
	cd quotes-fe && webdev build # --delete-conflicting-outputs

fe-run: 
	echo "running frontend"
	cd quotes-fe && webdev serve

fe-all: fe-format fe-get fe-build fe-run


be-format:
	cd quotes-be && dartfmt -w -l 160 --fix .

be-get: 
	echo "getting backend dependencies" 
	cd quotes-be && pub upgrade && pub get 
	
be-run: 
	echo "running backend"
	cd quotes-be && dart bin/run_app.dart infra/local.json

be-init:
	echo "Init db"
	./quotes-be/infra/init.sh
	


be-all: be-format be-get be-run