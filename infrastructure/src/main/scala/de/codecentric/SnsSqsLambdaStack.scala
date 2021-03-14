package de.codecentric

import software.amazon.awscdk.core.{Construct, Duration, Stack, StackProps}
import software.amazon.awscdk.services.lambda._
import software.amazon.awscdk.services.lambda.eventsources.SqsEventSource
import software.amazon.awscdk.services.sqs
import software.amazon.awscdk.services.sns
import software.amazon.awscdk.services.sns.subscriptions.SqsSubscription

class SnsSqsLambdaStack(scope: Construct, id: String, props: StackProps)
    extends Stack(scope, id, props) {
  private[this] val scalaVersion: String =
    BuildInfo.scalaVersion.split("""\.""").take(2).mkString(".")

  private[this] val queue = sqs.Queue.Builder.create(this, "queue").build()

  private[this] val topic = sns.Topic.Builder.create(this, "topic").build()

  topic.addSubscription(SqsSubscription.Builder.create(queue).build())

  private[this] val lambda = Function.Builder
    .create(this, "SnsSqsLambda")
    .runtime(Runtime.JAVA_11)
    .timeout(Duration.seconds(10))
    .memorySize(512)
    .handler(BuildInfo.snsSqsLambdaHandler)
    .code(
      Code.fromAsset(
        s"${BuildInfo.snsSqsLambdaDir}/target/scala-$scalaVersion/${BuildInfo.snsSqsLambdaJarName}"
      )
    )
    .build()

  lambda.addEventSource(SqsEventSource.Builder.create(queue).build())
}
