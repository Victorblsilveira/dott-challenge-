package dotchallenge.models

import java.time.LocalDateTime

case class Parameters(start: LocalDateTime, end: LocalDateTime, intervals: Option[Iterable[String]])
