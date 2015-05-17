import sbt._
import Keys._
 
object ApplicationBuild extends Build {
	val appName         = "scripts"
	val appVersion      = "0.1-SNAPSHOT"
	lazy val root = (project in file("."))
		.settings(
			//scalaVersion := "2.10.3"
			//,
			commands ++= Seq(backupCommand)
		)
	
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