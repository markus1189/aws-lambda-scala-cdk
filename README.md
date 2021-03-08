# Template for AWS Lambda in Scala with CDK

This is a template project that can be used to easily build and deploy
an AWS Lambda written in Scala.

## Quickstart

- make sure you have the aws-cdk installed (`npm install aws-cdk`)
- run `npx cdk diff` and if it sounds reasonable, `npx cdk deploy`
  (you might need a `cdk bootstrap`, but it will tell you)

## What is cool about this template?

- cdk also uses the `build.sbt`, more specifically the `infrastructure` submodule
- sbt makes sure that the fatjar is up-to-date before trying to do anything
- using Scala for both your lambda and your stacks
