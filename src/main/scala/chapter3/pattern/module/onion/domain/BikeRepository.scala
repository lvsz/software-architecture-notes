package chapter3.pattern.module.onion.domain

class BikeRepository:
  def rentBike(bike: Bike): Unit = bike.rent
  def returnBike(bike: Bike): Unit = bike.free
