package dotchallenge.models

import java.time.LocalDateTime

case class Product(
  name: String,
  price: String,
  weight: String,
  category: String,
  creationDate: LocalDateTime
)
