package chapter3.pattern.module.onion.api

import chapter3.pattern.module.onion.domain.DomainModule

trait APIModule:
  this: DomainModule =>
  val manageUser: ManageUser
  val manageBike: ManageBike
