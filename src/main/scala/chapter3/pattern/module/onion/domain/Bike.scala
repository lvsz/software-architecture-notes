package chapter3.pattern.module.onion.domain

sealed abstract class Bike:
  enum State:
    case Free, Rented
  private var state: State = State.Free
  def free = state = State.Free
  def rent = state = State.Rented
  def isFree: Boolean = state == State.Free
  val price: Int
  val color: Color

enum Color:
  case Red, Green, Blue

case class NormalBike() extends Bike:
  val price = 11
  val color = Color.Red
case class RoadBike() extends Bike:
  val price = 13
  val color = Color.Green
case class ElectricBike() extends Bike:
  val price = 19
  val color = Color.Blue
