name := """foy2016"""

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  // Uncomment to use Akka
  //"com.typesafe.akka" % "akka-actor_2.11" % "2.3.9",
  "org.jogamp.gluegen" % "gluegen-rt-main" % "2.3.2",
  "org.jogamp.jogl" % "jogl-all-main" % "2.3.2",
  "org.scijava" % "vecmath" % "1.6.0-scijava-2",
  "org.scijava" % "j3dcore" % "1.6.0-scijava-2",
  "org.scijava" % "j3dutils" % "1.6.0-scijava-2",
  "junit"             % "junit"           % "4.12"  % "test",
  "com.novocode"      % "junit-interface" % "0.11"  % "test"
)

resolvers ++= Seq(
  "Simulation @ TU Delft" at "http://simulation.tudelft.nl/maven/java3d",
  "scijava" at "https://mvnrepository.com/artifact/org.scijava/j3dcore"

)
