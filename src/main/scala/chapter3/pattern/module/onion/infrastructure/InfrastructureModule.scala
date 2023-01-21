package infrastructure

import api.{APIModule, ManageUser, ManageBike}
import domain.{DomainModule, UserRepository, BikeRepository}

trait InfrastructureModule:
  this: APIModule with DomainModule =>
  val userRepository: UserRepository = new UserRepository
  val bikeRepository: BikeRepository = new BikeRepository
  val manageUser: ManageUser = ManageUser(userRepository)
  val manageBike: ManageBike = ManageBike(userRepository, bikeRepository)
