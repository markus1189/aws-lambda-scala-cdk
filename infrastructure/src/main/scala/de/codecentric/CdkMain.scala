package de.codecentric

import software.amazon.awscdk.core.{App, Environment, StackProps}

import scala.util.chaining.scalaUtilChainingOps

object CdkMain extends scala.App {
  val environment = Environment
    .builder()
    .account(
      sys.env.getOrElse(
        "CDK_DEFAULT_ACCOUNT",
        throw new IllegalArgumentException("No default account found")
      )
    )
    .region(
      sys.env.getOrElse(
        "CDK_DEFAULT_REGION",
        throw new IllegalArgumentException("No default region found")
      )
    )
    .build()

  new App()
    .tap { app =>
      val firstLambda = new FirstLambdaStack(
        app,
        "first-lambda",
        StackProps
          .builder()
          .env(
            environment
          )
          .build()
      )
    }
    .synth()
}
