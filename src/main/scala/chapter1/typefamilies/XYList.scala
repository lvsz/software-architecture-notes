package chapter1.typefamilies

class XYList:
  type XType
  type YType

  class XList(var head: XType):
    var tail: YList = null
    def setTail(t: YList): Unit = tail = t

  class YList(var head: YType):
    var tail: XList = null
    def setTail(t: XList): Unit = tail = t

class IntStringList extends XYList:
  type XType = Int
  type YType = String

object XYList:
  object Example:
    def apply(): Unit =
      val isl = new IntStringList
      val x = isl.XList(42)
      val y = isl.YList("foo")
      x.setTail(y)
      println(x)
      // y.setTail(new isl.YList("illegal"))
