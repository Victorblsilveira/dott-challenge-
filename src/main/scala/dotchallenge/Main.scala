package dotchallenge

import akka.actor.ActorSystem
import dotchallenge.calculator.GroupsCalculator
import dotchallenge.data.DataGenerator
import dotchallenge.models.{Order, Parameters}
import dotchallenge.validator.Validator

import scala.concurrent.{ExecutionContext, Future}

object Main {

  // Starting an actor system to allow concurrency under the hoods
  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContext = actorSystem.dispatchers.lookup("dedicated-dispatcher")

  def main(args: Array[String]): Unit = {
    for {
      parameters <- validateArgs(args)
      orders <- generateData(parameters)
    } yield calculateGroups(parameters, orders)
  }

  private def validateArgs(args: Array[String]): Future[Parameters] = {
    Validator("yyyy-MM-dd HH:mm:ss").validate(args)
  }

  private def generateData(parameters: Parameters): Future[Iterable[Order]] = {
    DataGenerator(parameters).generate
  }

  private def calculateGroups(parameters: Parameters, orders: Iterable[Order]): Unit = {
    GroupsCalculator(orders, parameters).print()
  }
}
