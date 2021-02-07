package dotchallenge.validator

import dotchallenge.models.Parameters

import java.time.LocalDateTime
import java.time.format.{DateTimeFormatter, DateTimeParseException}
import scala.concurrent.{ExecutionContext, Future}

case class Validator(dateTimePattern: String)(implicit exc: ExecutionContext) {
  private val intervalRegex = "(.*)".r
  private val dateTimeParser: DateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)

  def validate(args: Array[String]): Future[Parameters] = Future {
    val params = paramsFromArgs(args)
    if (args.length > 2) {
      val intervals = intervalFromArgs(args)
      params.withIntervals(intervals)
    } else params
  }.recover {
    case ex: DateTimeParseException => throw new Exception("Parâmetros temporais no formato incorreto", ex)
    case ex: Throwable => throw new Exception("Parâmetros incorretos", ex)
  }

  def parse(date: String): LocalDateTime = { LocalDateTime.parse(date, dateTimeParser) }

  def paramsFromArgs(args: Array[String]): Parameters = {
    Parameters(
      parse(args(0)),
      parse(args(1)),
      None
    )
  }

  def intervalFromArgs(args: Array[String]): Option[Iterable[String]] = {
    val intervals = args(2)
    if (intervals.matches(intervalRegex.regex)) Some {
      args(2)
        .replace("(", "")
        .replace(")", "")
        .split(",")
        .map(_.trim).toIterable
    } else throw new Exception("Intervalos temporais no formato incorreto")
  }
}
