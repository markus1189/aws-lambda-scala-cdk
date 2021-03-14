package de.codecentric

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.amazonaws.services.lambda.runtime.events.SQSEvent

import scala.beans.BeanProperty
import scala.jdk.CollectionConverters.IteratorHasAsScala

class SnsSqsLambdaOutput(@BeanProperty var body: String = null) {}

class SnsSqsLambda extends RequestHandler[SQSEvent, SnsSqsLambdaOutput] {
  override def handleRequest(
      input: SQSEvent,
      context: Context
  ): SnsSqsLambdaOutput = {
    val logger = context.getLogger
    val numRecords = input.getRecords.size

    logger.log(s"Received $numRecords records")
    input.getRecords.iterator().asScala.zipWithIndex.foreach {
      case (msg, i) =>
        logger.log(s"Record[$i].attrs=${msg.getAttributes}")
        logger.log(s"Record[$i].body=${msg.getBody}")
    }

    new SnsSqsLambdaOutput(s"Finished processing $numRecords records")
  }
}
