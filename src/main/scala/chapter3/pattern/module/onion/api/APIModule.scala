package api

import domain.DomainModule

trait APIModule:
  this: DomainModule =>
  val manageUser: ManageUser
  val manageBike: ManageBike
