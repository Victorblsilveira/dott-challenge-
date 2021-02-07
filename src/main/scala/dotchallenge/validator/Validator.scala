package dotchallenge.validator

import dotchallenge.models.Parameters
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.concurrent.{ExecutionContext, Future}

case class Validator(dateTimePattern: String)(implicit exc: ExecutionContext) {

  private val dateTimeParser: DateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)

  def validate(args: Array[String]): Future[Parameters] = Future {
    val params = Parameters(
      parse(args(0)),
      parse(args(1)),
      None
    )

    if (args.length > 2) {
      params.copy(
        intervals = Some(
          args(2)
            .replace("(", "")
            .replace(")", "")
            .split(",")
            .map(_.trim).toIterable
        )
      )
    } else params
  }

  def parse(date: String): LocalDateTime = { LocalDateTime.parse(date, dateTimeParser) }
}
