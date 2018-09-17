#!/bin/bash

if [[ ! -z "$CASSANDRA_KEYSPACE" && $1 = 'cassandra' ]]; then
  # Create default keyspace for single node cluster
  CQL="
    CREATE KEYSPACE IF NOT EXISTS $CASSANDRA_KEYSPACE
    WITH REPLICATION = {
      'class': 'SimpleStrategy',
      'replication_factor': 1
    };

    USE $CASSANDRA_KEYSPACE;
    CREATE TABLE IF NOT EXISTS $CASSANDRA_INIT_TABLE
    (
      domain text PRIMARY KEY, score int
    );
  "

  until echo $CQL | cqlsh; do
    echo "cqlsh: Cassandra is unavailable - retry later"
    sleep 2
  done &
fi

exec /docker-entrypoint.sh "$@"