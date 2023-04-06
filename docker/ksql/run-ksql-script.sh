#!/bin/bash
BASE_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

KSQL_SERVER_URL=${KSQL_SERVER_URL:-http://ksql-server:8088}

# Wait until KSQL CLI endpoint is available
echo "Waiting for ksql-server endpoint"
i=0
# uses localhost since this is run by bsh script NOT docker
# ksql_server_url only recognized inside the docker container.
until response=$(curl --silent -o /dev/null -w "%{http_code}"  http://localhost:8088); do
  if [ $response -eq 000 ]; then
    printf '.'
	  sleep 3
	  ((i=i+1))
	  echo 'Connection attempt:' ${i}
    if [ $i -gt 25 ]; then
      echo "Timed out waiting for endpoint"
      exit 1
    fi
  else
    break
  fi
done

# Additional wait time, since sometimes it takes a while after endpoint responds
echo "\nEndpoint up, waiting for ksql-server initialization completion."
sleep 10

# Create Kafka streams and tables
echo "Creating streams and tables..."
cat $BASE_PATH/college_0_base.ksql | docker exec -i ksql-cli bash -c "ksql ${KSQL_SERVER_URL}"
