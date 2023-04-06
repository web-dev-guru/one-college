#!/bin/bash

KAFKA_CONNECTORS_URL=${KAFKA_CONNECTORS_URL:-http://localhost:8083/connectors}
POSTGRES_URL=${POSTGRES_URL:-jdbc:postgresql://db:5432/college}
POSTGRES_USER=${POSTGRES_USER:-antenna}
POSTGRES_PASSWORD=${POSTGRES_PASSWORD:-antenna}
ZOOKEEPER_HOST=${ZOOKEEPER_HOST:-zookeeper:2181}

# Wait until connectors endpoint is available
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

# Topics
echo "Creating mapping topics..."
docker exec kafka kafka-topics --create  --partitions 1 --replication-factor 1 --if-not-exists --config retention.ms=315360000000 --zookeeper ${ZOOKEEPER_HOST} --topic SBK_USER_MAPPINGS


# Markets
echo "Creating mapping connectors..."
curl -X POST $KAFKA_CONNECTORS_URL -H "Content-Type: application/json" --data @-  << EOF
{
  "name": "source_connect_jdbc_postgresql_user_mappings",
  "config":{
    "name":"source_connect_jdbc_postgresql_user_mappings",
    "connector.class":"io.confluent.connect.jdbc.JdbcSourceConnector",
    "tasks.max":1,
    "key.converter":"org.apache.kafka.connect.storage.StringConverter",
    "key.converter.schemas.enable":false,
    "value.converter":"org.apache.kafka.connect.json.JsonConverter",
    "value.converter.schemas.enable":false,
    "connection.url":"${POSTGRES_URL}",
    "connection.user":"${POSTGRES_USER}",
    "connection.password":"${POSTGRES_PASSWORD}",
    "dialect.name":"PostgreSqlDatabaseDialect",
    "schema.pattern":"sbk_feed",
    "query":"SELECT *, concat(id,'_',name) as row_key FROM college.user",
    "mode":"timestamp+incrementing",
    "timestamp.column.name":"modified",
    "incrementing.column.name":"id",
    "poll.interval.ms": 120000,
    "topic.prefix":"SBK_USER_MAPPINGS",
    "transforms":"createKey,extractString",
    "transforms.createKey.type":"org.apache.kafka.connect.transforms.ValueToKey",
    "transforms.createKey.fields":"row_key",
    "transforms.extractString.type":"org.apache.kafka.connect.transforms.ExtractField\$Key",
    "transforms.extractString.field":"row_key"
  }
}
EOF
