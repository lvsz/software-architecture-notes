package chapter3.pattern.module.onion.api

import chapter3.pattern.module.onion.domain.{Bike, BikeRepository, User, UserRepository}

case class ManageBike(
    userRepository: UserRepository,
    bikeRepository: BikeRepository
):
  def rentBike(user: User, bike: Bike) =
    println(s"${user.name} wants to rent $bike")
    if bike.isFree then
      bikeRepository.rentBike(bike)
      ManageUser(userRepository).payFee(user, bike.price)
    else println("Bike unavailable")

  def returnBike(user: User, bike: Bike) =
    println(s"${user.name} is returning $bike")
    bikeRepository.returnBike(bike)
    ManageUser(userRepository).returnFee(user, bike.price)
