package chapter3.pattern.module.onion.domain

trait DomainModule:
  val bikeRepository: BikeRepository
  val userRepository: UserRepository
