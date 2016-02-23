# How to configure

1. Put sbts.bat in PATH or in a location accessible from PATH
1. Change SCALA_SCRIPTS_HOME from sbts.bat to point to the current folder.
1. Add library dependencies in plugins.sbt
1. Add scripting commands in Build.scala

# How to execute
- `sbts` - enter in console mode where you can use the defined commands
- `sbts backup z:\1-personal-backups-auto\2015-04-27--archived--synchting-s4\` - to execute the backup command.

- incorporate
-- wmic /output:C:\InstallList.txt product get name