@echo off
set SCALA_SCRIPTS_HOME=d:\personal\bin
@pushd %SCALA_SCRIPTS_HOME% & sbt %* & popd

