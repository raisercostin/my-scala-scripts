import sbt._
import Keys._
import org.raisercostin.jedi._
import org.raisercostin.jedi.Locations.unixAndWindowsToStandard

object ApplicationBuild extends Build {
	val appName         = "scripts"
	val appVersion      = "0.1-SNAPSHOT"
	lazy val root = (project in file("."))
		.settings(
			scalaVersion := "2.11.7"
			//scalaVersion := "2.10.5"
			,commands ++= Seq(autoCommand, autoOrganizeCommand, backupCommand, extractPicsCmd, organizeCommand, uploadAllToBintray, doCommand, organizePfa, auto1)
			, libraryDependencies := Seq(
				"org.raisercostin" %% "jedi-io" % "0.25" excludeAll(
					ExclusionRule(organization="log4j")
					,ExclusionRule(organization="org.slf4j")
				)
				,"org.scala-sbt" % "sbt" % "0.13.9"
			)
			, resolvers += "raisercostin repository" at "http://dl.bintray.com/raisercostin/maven"
		)


	val rawLocations = Seq(Locations.file("""t:\incomming"""))

  def autoOrganizeCommand = Command.args("autoOrganize","execute all automatic organization stuff") { (state, args) =>
    openShareDrive

    closeShareDrive

    state
  }

  def auto1 = Command.args("all","execute all automatic organization") { (state, args) =>
    OrganizeFiles.auto

    state
  }

	def autoCommand = Command.args("auto","execute all automatic stuff") { (state, args) =>
		openShareDrive
		backupAppSettings
		closeShareDrive

		state
	}
	def openShareDrive = {
		println("""open t: <=> \\Homesyn2\data\master""")
		"""net use t: \\Homesyn2\data\master""".!!
	}
	def closeShareDrive={
		println("""close t: <=> \\Homesyn2\data\master""")
		"""net use /delete /y t:""".!!
	}
	def backupAppSettings={
		val clink = Locations.file("""c:\Users\costin\AppData\Local\clink\""")
		val backupOnSynology = Locations.file("""t:\1-personal-backups\auto-clink-history""")
		clink.child(".history").copyTo(backupOnSynology.child("history.txt").mkdirOnParentIfNecessary.renamedIfExists)
	}

	def backupCommand = Command.args("backup", "<folder>") { (state, args) =>
		if(args.size==0)
			println("pass a folder as argument")
		else{
			val file = args(0)
			val src = Locations.file(file)
			val dst = Locations.file(file).withName(_+"-backup").mkdirOnParentIfNecessary.renamedIfExists

			println(s"""backup $src to $dst""")
			src.descendants.map {x=>
				val rel = x.extractPrefix(src).get
				val y = dst.child(rel)
				println(f"""backup ${rel.raw}%-40s $x -> $y""")
				x.copyAsHardLink(y.mkdirOnParentIfNecessary.renamedIfExists)
			}
		}

		state
	}

	def extractPicsCmd = Command.args("organize", "<folder>") { (state, args) =>
		if(args.size!=1)
			println("Extract pics from the <folder> given as argument.")
		else{
			val file = args(0)
			val src = Locations.file(file).child("CallRecordings")
			val dst = Locations.file(file).withName(_+"-organized").mkdirOnParentIfNecessary.renamedIfExists

			println(s"""backup $src to $dst""")
			src.descendants.map {x=>
				val rel = x.extractPrefix(src).get
				val y = dst.child(rel)
				println(f"""backup ${rel.raw}%-40s $x -> $y""")
				x.copyAsHardLink(y.mkdirOnParentIfNecessary.renamedIfExists)
			}
		}

		state
	}
	def organizePfa = Command.args("organizePfa", "<folder>") { (state, args) =>
		renamePfIngAccounts

		state
	}
	def organizeCommand = Command.args("organize", "<folder>") { (state, args) =>
		renameBrainlightIngAccounts
		fixDuplicateExtensions
		makeCopiesToShareWithLaura

		state
	}

	val brainlight = Locations.file("""d:\personal\togo\docs-pj-brainlight\""")
	val brainlight2laura = brainlight.child("@laura")
	val pf = Locations.file("""d:\personal\togo\docs-pf\""")

	object AccountOwner extends Enumeration {
		type AccountOwner = Value
		val PF, PFA, BRAINLIGHT = Value
	}
	import AccountOwner._
	def renamePfIngAccounts = renameIngAccounts(pf,PF)
	def renameBrainlightIngAccounts = renameIngAccounts(brainlight,BRAINLIGHT)

	def renameIngAccounts(dest:NavigableOutputLocation, owner:AccountOwner) = {
		Locations.file("""d:\Downloads\""").list.filter{x=>
			x.name.startsWith("Extras_de_cont_20") ||
			x.name.startsWith("Account_Statement_20")
		}.foreach{x=>
			var name = x.name
			owner match {
				case BRAINLIGHT =>
					name = name.replaceAll("EUR \\(1\\)","EUX14")
					name = name.replaceAll("EUR","EUX77")
					name = name.replaceAll("EUX","EUR")
				case PF =>
					name = name.replaceAll("RON \\(1\\)","ROX20")
					name = name.replaceAll("RON","ROX10")
					name = name.replaceAll("ROX","RON")
					name = name.replaceAll("EUR \\(1\\)","EUX53")
					name = name.replaceAll("EUR","EUX13")
					name = name.replaceAll("EUX","EUR")
					name = name.replaceAll("USD","USD25")
			}
			name = name.replaceAll("Extras_de_cont_","")
			name = name.replaceAll("Account_Statement_","")
			name = name.replaceAll("_ianuarie_","-01-January--cont-")
			name = name.replaceAll("_februarie_","-02-February--cont-")
			name = name.replaceAll("_martie_","-03-March--cont-")
			name = name.replaceAll("_aprilie_","-04-April--cont-")
			name = name.replaceAll("_mai_","-05-May--cont-")
			name = name.replaceAll("_iunie_","-06-June--cont-")
			name = name.replaceAll("_iulie_","-07-July--cont-")
			name = name.replaceAll("_august_","-08-August--cont-")
			name = name.replaceAll("_septembrie_","-09-September--cont-")
			name = name.replaceAll("_octombrie_","-10-October--cont-")
			name = name.replaceAll("_noiembrie_","-11-November--cont-")
			name = name.replaceAll("_decembrie_","-12-December--cont-")
			val newName = dest.child(name).renamedIfExists
			println(s"""${x.name}->${newName}""")
			x.moveTo(newName)
		}
	}

	def fixDuplicateExtensions = {
		val src = brainlight
		src.list.filter(x=>x.baseName.endsWith("."+x.extension)).foreach{x=>
			println(x.name)
			x.renamed(_=>x.baseName.stripSuffix("."+x.extension))
		}
	}

	def makeCopiesToShareWithLaura={
		val src = brainlight
		src.list.filter(x=>x.name.contains("--cont-") && x.extension == "pdf")
			.foreach{x=>
				if(!brainlight2laura.child(x.name).exists){
					val newDest = brainlight2laura.child(x.name).renamedIfExists
					x.copyAsHardLink(newDest)
					println(s"""hardCopy $x->${newDest}""")
				}
			}
	}

	def uploadAllToBintray = Command.args("uploadAllToBintray", "bulk uploading/importing maven artefacts from a <folder>(from svn,local repo) to bintray.") { (state, args) =>
		if(args.size!=1)
			println("Extract pics from the <folder> given as argument.")
		else{
			//get an api key from https://bintray.com/profile/edit
			val apiKey="<apiKey>"
			val file = Locations.file(args(0))
			file.descendants.filterNot(_.path.contains(".svn")).map{x=>
				(x,x.extractPrefix(file).get)
			}.foreach{x=>
				val cmd = s"""curl -T ${x._1.absolute} -uraisercostin:$apiKey https://api.bintray.com/content/raisercostin/maven/${x._2.nameAndBefore}"""
				println(cmd)
				cmd.!!
			}
		}

		state
	}

	def doCommand = Command.args("do", "<folder>") { (state, args) =>
		if(args.size==0)
			println("pass a folder as argument")
		else{
			val file = args(0)
			val src = Locations.file(file)
			val dst = Locations.file(file).withName(_+"-backup").mkdirOnParentIfNecessary.renamedIfExists

			println(s"""backup $src to $dst""")
			src.descendants.filter(_.extension.toLowerCase=="pdf").map {x=>
				val rel = x.extractPrefix(src).get
				val y = dst.child(rel)
				println(f"""backup ${rel.raw}%-40s $x -> $y""")
				//println("to"+y.mkdirOnParentIfNecessary.withExtension(_=>""))
				//x.copyAsHardLink(y.mkdirOnParentIfNecessary.renamedIfExists)
				org.apache.pdfbox.ExtractImages.main(Array(x.absolute,"-prefix",y.mkdirOnParentIfNecessary.withExtension(_=>"").absolute))
			}
		}

		state
	}
}