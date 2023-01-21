package domain

final case class User(val name: String, var balance: Int):
  var online: Boolean = false
