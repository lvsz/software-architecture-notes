package chapter3.pattern.module.onion.api

import chapter3.pattern.module.onion.domain.{User, UserRepository}

case class ManageUser(userRepository: UserRepository):
  def createUser(name: String, balance: Int) =
    userRepository.createUser(name, balance)
  def logIn(user: User) = userRepository.logIn(user)
  def logOut(user: User) = userRepository.logOut(user)
  def payFee(user: User, fee: Int) = user.balance -= fee
  def returnFee(user: User, fee: Int) = user.balance += fee
