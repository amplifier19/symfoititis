#!/bin/bash

pg_host=$(docker inspect postgres | grep \"IPAddress | awk '{if (NR == 2) print $2}' | cut -d '"' -f2)
pg_port=5432
pg_user=symfoititis
users=(student admin keycloak)
dbs=(keycloak)

# Create users
for user in "${users[@]}"; do
	echo
	echo "Creating user ${user}"
	createuser -h $pg_host -p $pg_port -U $pg_user -DSRPe $user
done

# Create databases
for db in "${dbs[@]}"; do
	echo "Creating db ${db}"
	creatdb -h $pg_host -p $pg_port -U $pg_user -O $db $db
done

# Execute Init script
echo "Creating policies"
docker exec -it postgres psql -U $pg_user -d $db -f /initdb.sql
