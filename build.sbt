ThisBuild / scalaVersion := "2.13.5"
ThisBuild / organization := "de.codecentric"

lazy val versions = new {
  val awsCdk = "1.91.0"
  val awsLambdaCore = "1.2.1"
  val awsLambdaEvents = "3.7.0"
}

lazy val commonSettings = List(
  scalafmtOnCompile := true
)

lazy val commonAssemblySettings = List(assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last == "io.netty.versions.properties" =>
    MergeStrategy.first
  case PathList(ps @ _*) if ps.last == "module-info.class" =>
    MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
})

lazy val firstLambdaJarName = "first-lambda.jar"

lazy val `first-lambda` = (project in file("first-lambda"))
  .settings(
    name := "FirstLambda",
    libraryDependencies ++= List(
      "com.amazonaws" % "aws-lambda-java-core" % versions.awsLambdaCore,
      "com.amazonaws" % "aws-lambda-java-events" % versions.awsLambdaEvents
    ),
    assemblyJarName in assembly := firstLambdaJarName
  )
  .settings(commonAssemblySettings: _*)
  .settings(commonSettings)
  .enablePlugins(BuildInfoPlugin)

lazy val infrastructure = (project in file("infrastructure"))
  .settings(
    name := "Infrastructure",
    libraryDependencies ++= List(
      "software.amazon.awscdk" % "core" % versions.awsCdk,
      "software.amazon.awscdk" % "lambda" % versions.awsCdk
    ),
    (Compile / compile) := (Compile / compile)
      .dependsOn(
        `first-lambda` / assembly
      ) // make sure the lambda is up-to-date when running cdk
      .value
  )
  .settings(commonSettings)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys ++= List[BuildInfoKey](
      BuildInfoKey("firstLambdaJarName", firstLambdaJarName),
      BuildInfoKey("firstLambdaDir", "first-lambda"),
      BuildInfoKey("firstLambdaHandler", "de.codecentric.FirstLambda::handleRequest")
    ),
    buildInfoPackage := "de.codecentric"
  )

