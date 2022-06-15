#!/bin/bash

set -e
cd "$(dirname $0)/../.."
master_file="src/main/resources/db/changelog/db.changelog-master.xml"
changes_dir="src/main/resources/db/changelog/changes"

if [ $# -ne 1 ]; then
  echo "Usage: $0 name-of-the-migration"
  exit 1
fi

migration_name=$1
migration_id="$(date +%Y%m%d%H%M%S)-$migration_name"
migration_file="$migration_id.xml"
migration_path="$changes_dir/$migration_file"
temp_file=/tmp/create-migration.$$
username=$(id -F 2> /dev/null || echo $USER)

closing_tag="<\/databaseChangeLog>"
tag_to_insert="  <include relativeToChangelogFile='true' file='changes/$migration_file'/>"


# step one: add migration to changeLog
echo $master_file
awk "
/$closing_tag/ { print \"$tag_to_insert\" }
               { print \$0 }
" $master_file > $temp_file

mv $temp_file $master_file

# step two: add empty migration file
cat > "$migration_path" <<EOF
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                      http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.3.xsd">
  <changeSet author="$username" id="$migration_id">
      YOUR MIGRATION CODE HERE
    <rollback>
      YOUR ROLLBACK CODE HERE
    </rollback>
  </changeSet>
</databaseChangeLog>
EOF
