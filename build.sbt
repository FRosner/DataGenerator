import AssemblyKeys._

name := "DataGenerator"

version := "1.0alpha-SNAPSHOT"

libraryDependencies += "junit" % "junit" % "4.11" % "test"

libraryDependencies += "com.novocode" % "junit-interface" % "0.9" % "test"

libraryDependencies += "com.google.guava" % "guava" % "r05"

libraryDependencies += "org.easytesting" % "fest-assert" % "1.4" % "test"

libraryDependencies += "org.easytesting" % "fest-util" % "1.1.6" % "test"

libraryDependencies += "org.apache.commons" % "commons-math3" % "3.2"

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.1"

libraryDependencies += "commons-lang" % "commons-lang" % "2.3"

libraryDependencies += "org.mockito" % "mockito-all" % "1.8.4" % "test"

libraryDependencies += "jgraph" % "jgraph" % "5.13.0.0"

libraryDependencies += "jgraph" % "jgraphaddons" % "1.0.2"

libraryDependencies += "net.sf.jgrapht" % "jgrapht" % "0.8.3"

libraryDependencies += "net.sf.qualitycheck" % "quality-check" % "1.0-RC1"

parallelExecution in Test := false

test in assembly := {}
