package domain

class UserRepository:
  def createUser(name: String, balance: Int): User = User(name, balance)
  def logIn(user: User): Unit = user.online = true
  def logOut(user: User): Unit = user.online = false
