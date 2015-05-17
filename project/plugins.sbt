// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "raisercostin" at "https://raisercostin.googlecode.com/svn/maven2"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.4")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "3.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0")

libraryDependencies += "org.raisercostin" % "raisercostin-utils" % "0.7" excludeAll {
		  ExclusionRule(organization="org.slf4j",name="slf4j-log4j12")
		}