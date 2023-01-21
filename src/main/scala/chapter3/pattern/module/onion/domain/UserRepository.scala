package chapter3.pattern.module.onion.domain

class UserRepository:
  def createUser(name: String, balance: Int): User = User(name, balance)
  def logIn(user: User): Unit = user.online = true
  def logOut(user: User): Unit = user.online = false
