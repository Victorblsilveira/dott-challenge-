package dotchallenge.models

import java.time.LocalDateTime

case class Parameters(start: LocalDateTime, end: LocalDateTime, intervals: Option[Iterable[String]]) {
  def withIntervals(intervals: Option[Iterable[String]]): Parameters = copy(intervals = intervals)
}
