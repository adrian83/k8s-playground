
export DB_PASSWORD=secret
export DB_USERNAME=postgres
export DB_URL=jdbc:postgresql://localhost:5432/mordeczki
export DB_DRIVER=org.postgresql.Driver
export KAFKA_ADDRESS=localhost:29092


echo $KAFKA_ADDRESS


mvn spring-boot:run