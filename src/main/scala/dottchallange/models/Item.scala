package dottchallange.models

import java.time.LocalDateTime

case class Item(
  cost: String,
  product: Product,
  taxAmount: String,
  shippingFee: String,
)

/**
 * The others attributes doesnt really matters to challenge requirements
 * */
object Item {
  def apply(productCreationDateTime: LocalDateTime): Item = {
    Item("", Product("", "" , "", "", productCreationDateTime) , "", "")
  }
}