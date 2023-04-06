#!/bin/sh


current_script_location=$(dirname $0)
. $current_script_location/common_send_functions.sh

send_entity EVENT_FIXTURES_RAW '{"id": "456", "time": "2021-11-19T12:07:41.213745-08:00", "enabled": true, "name": 3232}'