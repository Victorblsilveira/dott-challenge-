package dotchallenge.calculator

import dotchallenge.models._

import java.time.LocalDateTime

case class GroupsCalculator(orders: Iterable[Order], parameters: Parameters) {

  private val mockedIntervals = Closed(1,3) :: Closed(4,6) :: Closed(7,12) :: RightOpen(12) :: Nil

  def print(): Unit = {
    groupByIntervals().foreach {
      case (interval, orders) => println(s" ${interval.toString}: ${orders.size} order")
    }
  }

  private def groupByIntervals(): Map[Interval, Iterable[Order]] = {
    parameters.intervals match {
      case None => groupByIntervals(mockedIntervals)
      case Some(customInterval) => groupByIntervals(customInterval.map(toInterval))
    }
  }

  private def groupByIntervals(intervals: Iterable[Interval]): Map[Interval, Iterable[Order]] = {
    intervals.map {
      case interval@(c: Closed) => interval -> filteredOrders.filter(closedTimeFilter(c, _))
      case interval@(ro: RightOpen) => interval -> filteredOrders.filter(rightTimeFilter(ro,_))
      case interval@(lo: LeftOpen) => interval -> filteredOrders.filter(leftOpenTimeFilter(lo, _))
      case a => throw new Exception(s"Intervalo não reconhecível ${a.getClass}")
    }.toMap
  }

  private def toInterval(range: String): Interval = range match {
    case LeftOpen(end) => LeftOpen(end)
    case RightOpen(start) => RightOpen(start)
    case Closed(start, end) => Closed(start, end)
    case _ => throw new Exception("Padrão de intervalo desconhecido")
  }

  private def closedTimeFilter(c: Closed, order: Order): Boolean = {
    differenceInMonths(order.date, parameters.start) >= c.start &&
    differenceInMonths(order.date, parameters.start) <= c.end &&
    order.sold
  }

  private def rightTimeFilter(ro: RightOpen, order: Order): Boolean = {
    differenceInMonths(order.date, parameters.start) > ro.start ||
    !order.sold
  }

  private def leftOpenTimeFilter(lo: LeftOpen, order: Order): Boolean = {
    differenceInMonths(order.date, parameters.start) < lo.end &&
    order.sold
  }

  private def filteredOrders = orders.filter(filterOrderByDate)

  private def filterOrderByDate(order: Order): Boolean = {
    order.date.isAfter(parameters.start) && order.date.isBefore(parameters.end)
  }

  private def differenceInMonths(timeA: LocalDateTime, timeB: LocalDateTime): Long = {
    val yearDiff = timeA.getYear - timeB.getYear
    val monthDiff = timeA.getMonthValue - timeB.getMonthValue
    yearDiff * 12 + monthDiff
  }
}
