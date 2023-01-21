package chapter3.pattern.module.onion.infrastructure

import chapter3.pattern.module.onion.api.APIModule
import chapter3.pattern.module.onion.domain.{DomainModule, User, Bike}

object UI extends InfrastructureModule with APIModule with DomainModule:
  def rentBike(user: User, bike: Bike) =
    manageBike.rentBike(user, bike)

  def returnBike(user: User, bike: Bike) =
    manageBike.returnBike(user, bike)
