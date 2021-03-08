package de.codecentric

import software.amazon.awscdk.core.{Construct, Duration, Stack, StackProps}
import software.amazon.awscdk.services.lambda._

class FirstLambdaStack(scope: Construct, id: String, props: StackProps)
    extends Stack(scope, id, props) {
  private[this] val scalaVersion: String =
    BuildInfo.scalaVersion.split("""\.""").take(2).mkString(".")

  Function.Builder
    .create(this, "FirstLambda")
    .runtime(Runtime.JAVA_11)
    .timeout(Duration.seconds(10))
    .memorySize(512)
    .handler(BuildInfo.firstLambdaHandler)
    .code(
      Code.fromAsset(
        s"${BuildInfo.firstLambdaDir}/target/scala-$scalaVersion/${BuildInfo.firstLambdaJarName}"
      )
    )
    .build()
}
