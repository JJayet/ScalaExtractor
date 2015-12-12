name := "ScalaExtractor"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "ApacheSnapshot" at "https://repository.apache.org/content/groups/snapshots/"

libraryDependencies += "com.beachape.filemanagement" %% "schwatcher" % "0.1.8"

libraryDependencies += "org.apache.pdfbox" % "pdfbox-app" % "2.0.0-SNAPSHOT"