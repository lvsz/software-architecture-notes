package domain

trait DomainModule:
  val bikeRepository: BikeRepository
  val userRepository: UserRepository
