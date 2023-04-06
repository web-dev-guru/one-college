#!/bin/sh

set -eux

function send_entity() {
  output_topic=$1
  current_data_json=$2
  action=${3:-I}
  current_data_as_escaped_string=$(printf "%s\n" "$current_data_json" | jq '. | @json')
  json='{"action": "'$action'", "current_data": '$current_data_as_escaped_string'}'
  message_json=$(printf "%s" "$json" | jq -c)
  printf "%s" "$message_json" | docker-compose -f ../docker-compose-kafka-only.yml exec -T kafka //usr/bin/kafka-console-producer --broker-list kafka:29092 --topic "$output_topic"

}
