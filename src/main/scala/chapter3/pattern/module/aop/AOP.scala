package chapter3.pattern.module.aop

/** Aspect-Oriented Pattern aspects inserts "advice code" before and/or after
  * join points join points specified by pointcut expressions, for built-in
  * scala, these are trait mixins
  */
object AOP:
  trait Channel:
    def send(x: String) = println(x)

  // possible because using `super` in traits is late-bound in scala
  trait LogBefore extends Channel:
    abstract override def send(x: String) =
      println("before!")
      super.send(x)
  trait LogAfter extends Channel:
    abstract override def send(x: String) =
      super.send(x)
      println("after!")

  object Channel:
    val channel = new Channel with LogAfter with LogBefore
    def apply(s: String): Unit = channel.send(s)
