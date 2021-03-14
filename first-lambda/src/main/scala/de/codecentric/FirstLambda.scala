package de.codecentric

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

import scala.beans.BeanProperty

class FirstLambdaOutput(@BeanProperty var body: String = null) {}

class FirstLambda
    extends RequestHandler[java.util.Map[String, Any], FirstLambdaOutput] {
  override def handleRequest(
      input: java.util.Map[String, Any],
      context: Context
  ): FirstLambdaOutput = {

    context.getLogger.log(s"$input")

    new FirstLambdaOutput("Hello World")
  }
}
