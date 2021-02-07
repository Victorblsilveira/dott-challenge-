package dotchallenge.data

import dotchallenge.models.{Item, Order, Parameters}

import java.time.{Instant, LocalDateTime, ZoneOffset}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

case class DataGenerator(parameters: Parameters)(implicit exc: ExecutionContext) {

  def generate: Future[Iterable[Order]] = Future {
    if (start.isAfter(end)) throw new Exception(s"Wrong date interval parameters start: $start end: $end")
    val numberOfOrders = Random.between(1000, 2000)
    Iterable.fill(numberOfOrders)(generateOrder)
  }

  private def end =  parameters.end
  private def start = parameters.start

  private def generateOrder: Order = {
    Order(randomDate, randomBoolean, generateItems)
  }

  private def generateItems: Iterable[Item] = {
    val random = Random.between(1, 10)
    Iterable.fill(random)(Item(randomDate))
  }

  // For performance improvements maybe is best to use an stateful approach instead of
  // creating a random instance every time.
  private def randomDate: LocalDateTime = {
    val startMillis = start.toInstant(ZoneOffset.UTC).toEpochMilli
    val endMillis = end.toInstant(ZoneOffset.UTC).toEpochMilli
    val newDateTimeMillis = Random.between(startMillis, endMillis)
    val newDateTime = Instant.ofEpochMilli(newDateTimeMillis)
    LocalDateTime.ofInstant(newDateTime, ZoneOffset.UTC)
  }

  private def randomBoolean: Boolean = {
    val rand = Random.between(0,2)
    if (rand == 1) true
    else false
  }
}