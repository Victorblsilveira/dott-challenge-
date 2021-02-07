package dotchallenge.models

import java.time.LocalDateTime

case class Order(
  grand: Long,
  address: String,
  date: LocalDateTime,
  customer: String,
  contact: Contact,
  sold: Boolean,
  items: Iterable[Item],
)

object Order {
  def apply(date: LocalDateTime, sold: Boolean, items: Iterable[Item]): Order = Order(-1, "", date, "", Contact(""), sold, items)
}