package de.codecentric

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

import scala.beans.BeanProperty
import scala.util.chaining.scalaUtilChainingOps

class FirstLambdaInput() {
  @BeanProperty var key1: String = _
  @BeanProperty var key2: String = _
  @BeanProperty var key3: String = _
}

class FirstLambdaOutput() {
  @BeanProperty var body: String = _
}

class FirstLambda extends RequestHandler[FirstLambdaInput, FirstLambdaOutput] {
  override def handleRequest(
      input: FirstLambdaInput,
      context: Context
  ): FirstLambdaOutput = {
    context.getLogger.log(s"key1 = ${input.key1}")
    context.getLogger.log(s"key2 = ${input.key2}")
    context.getLogger.log(s"key3 = ${input.key3}")

    (new FirstLambdaOutput).tap(_.setBody("Hello, World!"))
  }
}
