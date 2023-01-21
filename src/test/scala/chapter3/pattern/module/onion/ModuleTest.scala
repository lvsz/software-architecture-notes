package chapter3.pattern.module.onion

import org.scalatest.flatspec.AnyFlatSpec
import infrastructure.{InfrastructureModule, UI}
import domain.{NormalBike, ElectricBike, RoadBike}
import api.APIModule
import domain.DomainModule

// import chapter3.pattern.module.onion.infrastructure.UI

class ModuleTest
    extends AnyFlatSpec
    with InfrastructureModule
    with APIModule
    with DomainModule:

  class RentFixture:
    val user1 = manageUser.createUser("Alice", 50)
    val user2 = manageUser.createUser("Bob", 12)
    val bike1 = ElectricBike()

  def fixture = new RentFixture

  "Renting a bike" should "correctly reduce renter's balance" in {
    val f = fixture
    val initBalance = f.user1.balance
    UI.rentBike(f.user1, f.bike1)
    assert(f.user1.balance < initBalance)
    assert(f.user1.balance + f.bike1.price == initBalance)
  }
  it should "not allow the same bike to get rented again" in {
    val f = fixture
    val initBalance = f.user1.balance
    UI.rentBike(f.user2, f.bike1)
    UI.rentBike(f.user1, f.bike1)
    assert(f.user1.balance == initBalance)
  }
  it should "restore balance after returning the bike" in {
    val f = fixture
    val initBalance = f.user1.balance
    UI.rentBike(f.user1, f.bike1)
    assert(f.user1.balance != initBalance)
    UI.returnBike(f.user1, f.bike1)
    assert(f.user1.balance == initBalance)
  }
