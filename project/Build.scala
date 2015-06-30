import sbt._
import Keys._
import org.raisercostin.jedi._
import org.raisercostin.jedi.Locations.unixAndWindowsToStandard

object ApplicationBuild extends Build {
	val appName         = "scripts"
	val appVersion      = "0.1-SNAPSHOT"
	lazy val root = (project in file("."))
		.settings(
			//scalaVersion := "2.10.3"
			//,
			commands ++= Seq(autoCommand, backupCommand, extractPicsCmd, organizeCommand, uploadAllToBintray)
		)
	
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
	def organizeCommand = Command.args("organize", "<folder>") { (state, args) =>
		renameBrainlightIngAccounts
		fixDuplicateExtensions
		makeCopiesToShareWithLaura
		backupAppSettings
		
		state
	}

	val brainlight = Locations.file("""d:\personal\work\conta\brainlight\""")
	val brainlight2laura = brainlight.withName(_+"2laura")
	
	def renameBrainlightIngAccounts = {
		val dest = brainlight
		Locations.file("""d:\Downloads\""").list.filter(_.name.startsWith("Extras_de_cont_2015_")).foreach{x=>
			var name = x.name
			name = name.replaceAll("EUR \\(1\\)","EUR14")
			name = name.replaceAll("EUR","EUR77")
			name = name.replaceAll("Extras_de_cont_","")
			name = name.replaceAll("_martie_","-03-March--cont-")
			name = name.replaceAll("_aprilie_","-04-April--cont-")
			println(s"""${x.name}->${name}""")
			x.moveTo(dest.child(name))
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
					x.copyAsHardLink(brainlight2laura.child(x.name))
					println(s"""hardCopy $x->${brainlight2laura.child(x.name)}""")
				}
			}
	}
	def autoCommand = Command.args("auto","execute all automatic stuff") { (state, args) =>
		openShareDrive
		backupAppSettings
		closeShareDrive
		
		state
	}
	
	def openShareDrive = {
		println("""open t: <=> \\Homesyn\media\master""")
		"""net use t: \\Homesyn\media\master""".!!
	}
	def closeShareDrive={
		println("""close t: <=> \\Homesyn\media\master""")
		"""net use /delete /y t:""".!!
	}
	def backupAppSettings={
		val clink = Locations.file("""c:\Users\costin\AppData\Local\clink\""")
		val backupOnSynology = Locations.file("""t:\1-personal-backups\auto-clink-history""")
		clink.child(".history").copyTo(backupOnSynology.child("history.txt").mkdirOnParentIfNecessary.renamedIfExists)
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
}