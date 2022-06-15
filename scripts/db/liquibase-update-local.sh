#!/bin/bash
export DB_URL="jdbc:postgresql://172.28.94.79:9001/db-knowledge-sharing"
export DB_USER="dbkwnsha"
export DB_PASSWORD="dbkwnsha"

echo "---- Running liquibase with for User $DB_USER ----"
java -jar ./scripts/db/liquibase-core-3.5.3.jar \
  --changeLogFile="src/main/resources/db/changelog/db.changelog-master.xml" \
  --username="$DB_USER" \
  --password="$DB_PASSWORD" \
  --url="$DB_URL" \
  --classpath="./scripts/db/postgresql.jar:." \
  update
