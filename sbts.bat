@echo off
set SCALA_SCRIPTS_HOME=d:\personal\bin\my-scala-scripts
@pushd %SCALA_SCRIPTS_HOME% & sbt %* & popd

