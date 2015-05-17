import sbt._
import Keys._
import play.Project._
import com.typesafe.sbt.SbtScalariform.scalariformSettings
import com.typesafe.sbt.SbtScalariform._
import scalariform.formatter.preferences._
 
object ApplicationBuild extends Build {
	val appName         = "namek"
	val appVersion      = "0.6-SNAPSHOT"
    val javaVersion		= "1.7.0_67"
    //val scalaVersion	= "2.10.3"
	val playVersion		= "2.2.4"
	val akkaVersion  = "2.2.4"
	//val akkaVersion  = "2.3.6"
	//val sprayVersion = "1.3.2"
	val sprayVersion = "1.2.2"
	val exclusions = {
		  ExclusionRule(organization="org.slf4j",name="slf4j-log4j12")
		}
	val appDependencies = Seq(
		// Add your project dependencies here,
		jdbc
		//,
		//anorm
		//  )
		//libraryDependencies ++= Seq(
		// Select Play modules
		//jdbc,      // The JDBC connection pool and the play.api.db API
		//anorm,     // Scala RDBMS Library
		//javaJdbc,  // Java database API
		//javaEbean, // Java Ebean plugin
		//javaJpa,   // Java JPA plugin
		//filters,   // A set of built-in filters
		//,javaCore  // The core Java API
		// WebJars pull in client-side web libraries
		//uncomment if you want the sources?
		//,"com.typesafe.play"   %% "play"                % playVersion withSources
		//webjars
		,"org.webjars" %% "webjars-play" % "2.2.0"
		//,"org.webjars" % "bootstrap" % "2.3.1"
		//,"org.webjars" % "bootstrap" % "3.1.1"
		,"org.webjars" % "bootstrap" % "3.2.0"
		,"org.webjars" % "font-awesome" % "4.2.0"
		
		// Add your own project dependencies in the form:
		// "group" % "artifact" % "version"
		//downloader
		,"org.scalaj" %% "scalaj-http" % "0.3.14"
		,"net.sf.jtidy" % "jtidy" % "r938"
		,"com.github.scala-incubator.io" %% "scala-io-core" % "0.4.2"
		,"com.github.scala-incubator.io" %% "scala-io-file" % "0.4.2"
		,"org.jdom" % "jdom2" % "2.0.5"
		,"jaxen" % "jaxen" % "1.1.6" 
		,"org.fluentlenium" % "fluentlenium-core" % "0.9.2" 
		,"ws.securesocial" %% "securesocial" % "2.1.3"
		//,"ws.securesocial" %% "securesocial" % "master-SNAPSHOT"
		,"org.squeryl" %% "squeryl" % "0.9.6-RC2"
		,"com.netflix.rxjava" % "rxjava-scala" % "0.15.0"
		,"junit" % "junit" % "4.10" % "test"
		,"org.json4s" % "json4s-native_2.10" % "3.2.5"
		,"net.databinder.dispatch" % "dispatch-core_2.10" % "0.11.0"
		//,"org.slf4j" % "slf4j-api" % "1.7.5"
		//,"org.slf4j" % "slf4j-simple" % "1.7.5"
		,"com.squareup.retrofit" % "retrofit" % "1.0.0"
		,"org.scala-lang" % "scala-swing" % "2.10.3"
		,"org.scala-lang" % "scala-reflect" % "2.10.3"
		,"org.scala-lang.modules" %% "scala-async" % "0.9.0-M2"
		//,"com.typesafe.akka" %% "akka-actor" % "2.2.3"
		//,"com.typesafe.akka" %% "akka-testkit" % "2.2.3"
		,"com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
		,"com.typesafe.akka" %% "akka-actor" % akkaVersion
		,"com.typesafe.akka" %% "akka-slf4j" % akkaVersion

		
		//slick for play
		//,"com.typesafe.play" %% "play-slick" % "0.8.0" 
		,"com.typesafe.play" %% "play-slick" % "0.6.0.1" 
/*
		//slick
		,"com.typesafe.slick" %% "slick" % "2.1.0"
		//,"org.slf4j" % "slf4j-nop" % "1.6.4"
		,"com.h2database" % "h2" % "1.3.170"
		,"org.xerial" % "sqlite-jdbc" % "3.7.2"		
		//slick with play
		//,"org.webjars" %% "webjars-play" % "2.2.1"
		//,"org.webjars" % "bootstrap" % "2.3.2"
		,"com.typesafe.slick" %% "slick" % "2.0.1-RC1"
		,"com.typesafe.play" %% "play-slick" % "0.5.0.8"
		,"org.virtuslab" %% "unicorn" % "0.4"
*/
		//slick joda time
		//,"com.typesafe.slick" %% "slick" % "2.0.0"
		,"joda-time" % "joda-time" % "2.3"
		,"org.joda" % "joda-convert" % "1.5"
		,"com.github.tototoshi" %% "slick-joda-mapper" % "1.0.1"
		
		//selenium
		//,"org.seleniumhq.selenium" % "selenium-java" % "2.35.0"
		
		//pdfbox
		,"org.apache.pdfbox" % "pdfbox-app" % "1.8.4"
		
		//poi
		,"org.apache.poi" % "poi" % "3.9"
		,"org.apache.poi" % "poi-ooxml" % "3.9"
		,"org.apache.poi" % "poi-ooxml-schemas" % "3.9"
		,"org.apache.poi" % "poi-scratchpad" % "3.9"
		//org/apache/poi/ooxml-schemas/1.0/ooxml-schemas-1.0.jar
		
		//scrap
		,"org.jsoup" % "jsoup" % "1.6.3"
		,"org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1"
		//,"com.syncthemall" % "goose" % "2.1.25"
		//"com.gravity" % "goose_2.10.2" % "2.1.23"
		,"com.gravity.goose" %% "goose" % "2.2.3" excludeAll (
				ExclusionRule(organization="com.google.appengine"),
				ExclusionRule(organization="org.slf4j",name="slf4j-simple")
			)
		
		//parsers in macros
		,"org.parboiled" %% "parboiled" % "2.0.1"
		//scala IO (replacing my Locations)
		,"com.propensive" %% "rapture-io" % "0.9.1"
		,"org.raisercostin" % "raisercostin-common" % "2.20" excludeAll exclusions
		
		//spray
		,"io.spray" % "spray-can" % sprayVersion
		//,"org.raisercostin" % "raisercostin-utils" % "0.7"
		,"com.univocity" % "univocity-parsers" % "1.4.0"
    ,"org.scalatest" %% "scalatest" % "2.2.4" //% "test"
		,"org.scalacheck" %% "scalacheck" % "1.12.2" % "test"
    ,"hr.element.etb" %% "scala-transliteration" % "0.0.1"
		//stemming
		,"org.tartarus.snowball" % "libstemmer" % "1.0.0"
		,"com.typesafe.akka" %% "akka-stream-experimental" % "1.0-RC1"
	)
	//val moduleUsers = play.Project(appName+"-users", appVersion, path = file("modules/users"))
    import com.typesafe.sbteclipse.core.EclipsePlugin._
	val main = play.Project(appName, appVersion, appDependencies)
		.settings(play.Project.playScalaSettings:_*)
		.settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*)
		.settings(
			// Add your own project settings here
			scalaVersion := "2.10.3"
			,resolvers ++= Seq(
			    Resolver.url("maven central",url("http://central.maven.org/maven2"))
				,Resolver.url("Objectify Play Repository (release)", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns)
				,Resolver.url("Objectify Play Repository (snapshot)", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns)
				,Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns)
				,Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns)
				,Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns)
				,Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns)
				,"raisercostin" at "https://raisercostin.googlecode.com/svn/maven2"
				,"JLangDetect Maven repository" at "https://jlangdetect.googlecode.com/svn/repo"
				,Resolver.sonatypeRepo("releases")
				,Resolver.sonatypeRepo("snapshots")
				,"spray repo" at "http://repo.spray.io"
        ,"Element Releases" at "http://repo.element.hr/nexus/content/repositories/releases/"
			)
			,commands ++= Seq(releaseList,releaseCommand,shCommand,backupCommand)
			,unmanagedSourceDirectories in Compile += baseDirectory.value / "src/main/scala"
			,unmanagedResourceDirectories in Compile += baseDirectory.value / "src/main/resources"
			,unmanagedSourceDirectories in Test := Seq(baseDirectory.value / "src/test/scala")
			//,unmanagedResourceDirectories in Test += baseDirectory.value / "src/test/resources"
			,resourceDirectory in Test <<= baseDirectory(_/ "src/test/resources")
			,EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
			,EclipseKeys.withSource := true
		)
		.settings(scalariformSettings:_*)
		.settings(
			//sbt.Keys.fork in run := true,
			sbt.Keys.fork := true,
			javaOptions ++= Seq(
				//-Xms300m -Xmx300m -XX:PermSize=256m -XX:MaxPermSize=256m -Xss1M -XX:ReservedCodeCacheSize=2m -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:-PrintGCDetails -XX:+PrintCommandLineFlags
				//memory constraints
				"-Xms300m", "-Xmx300m",
				"-XX:PermSize=256m", "-XX:MaxPermSize=256m",
				"-Xss1M",
				"-XX:ReservedCodeCacheSize=2m",
				//choose Garbage collector
				"-XX:+UseConcMarkSweepGC",
				//"-XX:+UseG1GC", //for jdk6 add "-XX:+UnlockExperimentalVMOptions",
				//remote debugging options
				//"-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005",
				//others
				"-XX:+CMSClassUnloadingEnabled", 
				//report settings
				"-XX:-PrintGCDetails", "-XX:+PrintCommandLineFlags"),
			ScalariformKeys.preferences := ScalariformKeys.preferences.value
				  .setPreference(AlignSingleLineCaseStatements, true)
				  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
				  .setPreference(DoubleIndentClassDeclaration, true)
				  .setPreference(PreserveDanglingCloseParenthesis, true)
		)
		//.dependsOn(moduleUsers)
		//.aggregate(moduleUsers)  
	//scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation","-feature")
	val base = """svn://raisercostin.synology.me/repo/dist/org/raisercostin/namek-dist"""
	
	def releaseList = Command.command("list"){ state =>
		val list = s"""svn list $base/tags """
		println(list)
		list !
		
		state
	}
	
	def releaseCommand = Command.args("release", "<tag>") { (state, args) =>
	
		val tag = s"""svn copy $base/trunk $base/tags/${args(0)} -m "release" """
		println(s"\nexecute $tag")
		tag !

		val delete = s"""svn delete $base/release -m "delete" """
		println(s"\nexecute $delete")
		delete !

		val release = s"""svn copy $base/tags/${args(0)} $base/release -m "release" """
		println(s"\nexecute $release")
		release !
		
		val list = s"""svn list $base/tags """
		println(list)
		list !

		state
    }
	
	def shCommand = Command.args("sh", "<tag>") { (state, args) =>
		val all = args.mkString(" ") !
		
		state
	}
	
	def backupCommand = Command.args("backup", "<folder>") { (state, args) =>
		if(args.size==0)
			println("pass a folder as argument")
		else{
			val file = args(0)
			import org.raisercostin.util.io._
			val src = Locations.file(file)
			val dst = Locations.file(file).withName(_+"-backup").mkdirOnParentIfNecessary.renamedIfExists
			
			println(s"""backup $src to $dst""")
			src.descendants.map {x=>
				val rel = x.extractPrefix(src)
				val y = dst.child(rel)
				println(f"""backup ${rel.raw}%-40s $x -> $y""")
				x.copyAsHardLink(y.mkdirOnParentIfNecessary.renamedIfExists)
			}
		}
		
		state
	}
}


  
//  libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _) 
  
//  scalacOptions += "-deprecation" 
