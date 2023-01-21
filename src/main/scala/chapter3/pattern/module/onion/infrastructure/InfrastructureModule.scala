package chapter3.pattern.module.onion.infrastructure

import chapter3.pattern.module.onion.api.{APIModule, ManageUser, ManageBike}
import chapter3.pattern.module.onion.domain.{
  DomainModule,
  UserRepository,
  BikeRepository
}

trait InfrastructureModule:
  this: APIModule with DomainModule =>
  val userRepository: UserRepository = new UserRepository
  val bikeRepository: BikeRepository = new BikeRepository
  val manageUser: ManageUser = ManageUser(userRepository)
  val manageBike: ManageBike = ManageBike(userRepository, bikeRepository)
