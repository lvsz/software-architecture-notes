package domain

class BikeRepository:
  def rentBike(bike: Bike): Unit = bike.rent
  def returnBike(bike: Bike): Unit = bike.free
