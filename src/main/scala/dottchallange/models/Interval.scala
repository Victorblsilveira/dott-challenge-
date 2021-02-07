package dottchallange.models

import Interval.ArrayExtension

sealed trait Interval {
  def toString: String
}

case class Closed(start: Long, end: Long) extends Interval {
  override def toString: String = s"Closed Interval:  $start - $end"
}

object Closed {
  private val closedRange = ".*-.*".r

  def unapply(text: String): Option[(Long,Long)] = {
    if (text.matches(closedRange.regex)) Some(text.split("-").map(_.toLong).tuple2)
    else None
  }

}

case class RightOpen(start: Long) extends Interval {
  override def toString: String = s"RightOpen Interval:  > $start"
}

object RightOpen {
  private val rightOpenRegex = "> .*".r

  def unapply(text: String): Option[Long] = {
    if (text.matches(rightOpenRegex.regex)) Some(text.split(">").last.toLong)
    else None
  }
}


case class LeftOpen(end: Long) extends Interval {
  override def toString: String = s"LeftOpen Interval:  < $end"
}

object LeftOpen {
  private val leftOpenRegex = "< .*".r

  def unapply(text: String): Option[Long] = {
    if (text.matches(leftOpenRegex.regex)) Some(text.split("<").last.toLong)
    else None
  }
}

object Interval {

  implicit class ArrayExtension[T](val array: Array[T]) extends AnyVal() {
    def tuple2: (T, T) = (array(0), array(1))
  }
}


