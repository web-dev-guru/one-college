docker-build:
	mvn install -f "../pom.xml" \
		-Dmaven.test.skip=true && \
	docker build \
		-f ../Dockerfile \
		-t one-college ../

kafka:
	docker-compose \
		-f ../docker-compose-kafka-only.yml \
		up

kafka-down:
	docker-compose \
		-f ../docker-compose-kafka-only.yml \
		down

college-mongo-run:
	docker-compose \
		-f ../docker-compose-college-mongo.yml \
		up -d

kafka-topic-connect-setup:
	MARKET_RETENTION="86400000" MARKET_DELETE_RETENTION="86400000" ./topics/config-topics.sh
	./ksql/run-ksql-script.sh
	POSTGRES_URL="jdbc:postgresql://db:5432/college?stringtype=unspecified" POSTGRES_USER="antenna" POSTGRES_PASSWORD="antenna" KAFKA_SCHEMA_REGISTRY_URL="http://schema-registry:8081" ./connectors/setup-connectors.sh

kafka-stream-setup:
	MARKET_RETENTION="86400000" MARKET_DELETE_RETENTION="86400000" ./ksql/run-ksql-script.sh

