package chapter3.pattern.module.onion.domain

final case class User(val name: String, var balance: Int):
  var online: Boolean = false
