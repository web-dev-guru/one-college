#!/bin/bash

KAFKA_CONNECTORS_URL=${KAFKA_CONNECTORS_URL:-http://localhost:8083/connectors}
ZOOKEEPER_HOST=${ZOOKEEPER_HOST:-zookeeper:2181}
MARKET_RETENTION=${MARKET_RETENTION:-0}
MARKET_DELETE_RETENTION=${MARKET_DELETE_RETENTION:-10800000}

# Wait until connectors endpoint is available (which depends on zoopkeeper
# and kafka, so it'a a good indication that topics can be created)
echo "Waiting for connectors endpoint"
i=0
until $(curl --output /dev/null --silent --head --fail $KAFKA_CONNECTORS_URL); do
	printf '.'
	sleep 5
	((i=i+1))
	if [ $i -gt 25 ]; then
		echo "Timed out waiting for endpoint"
		exit 1
	fi
done

# Additional wait time, since sometimes it takes a while after endpoint responds
echo "\nEndpoint up, waiting for initialization completion."
sleep 5

# Topics
echo "Creating college topics..."
docker exec kafka kafka-topics --create --if-not-exists --topic EVENT_FIXTURES_RAW --partitions 1 --replication-factor 1 --config cleanup.policy=delete --config retention.ms=43200000 --zookeeper ${ZOOKEEPER_HOST}
