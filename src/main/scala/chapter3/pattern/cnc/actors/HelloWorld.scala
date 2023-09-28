package chapter3.pattern.cnc.actors

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}

object HelloWorld:
  final case class Greet(whom: String, replyTo: ActorRef[Greeted])
  final case class Greeted(whom: String, from: ActorRef[Greet])

  def apply(): Behavior[Greet] =
    // Factory method taking both context and message
    // Behaviors.setup only takes context
    // Behaviors.receiveMessage only takes message
    // actor = message processing behavior + context in which to execute it
    Behaviors.receive { (context, message) =>
      println(s"Hello ${message.whom}!")
      // `!` is the message sending operator
      message.replyTo ! Greeted(message.whom, context.self)
      // tell receiver of message to have same behavior
      Behaviors.same
    }

object HelloWorldBot:
  def apply(max: Int): Behavior[HelloWorld.Greeted] =
    bot(0, max)

  def bot(greetingCounter: Int, max: Int): Behavior[HelloWorld.Greeted] =
    Behaviors.receive { (context, message) =>
      val n = greetingCounter + 1
      println(s"Greeting $n for ${message.whom}")
      if (n == max) then
        // if n == max, we can stop greeting
        Behaviors.stopped
      else
        // otherwise, reply to sender with a greeting to message.whom
        message.from ! HelloWorld.Greet(message.whom, context.self)
        bot(n, max)
    }

object HelloWorldMain:
  case class SayHello(name: String)
  // factory for when actor is started
  def apply(): Behavior[SayHello] =
    Behaviors.setup { context =>
      // spawn a child actor, called "greeter"
      val greeter = context.spawn(HelloWorld(), "greeter")
      // when asked to SayHello, create a bot to greet for us
      Behaviors.receiveMessage { message =>
        val replyTo = context.spawn(HelloWorldBot(max = 3), message.name)
        greeter ! HelloWorld.Greet(message.name, replyTo)
        Behaviors.same
      }
    }

  @main
  def functionalHelloWorldMain() =
    // actors for a hierarchy, this one will be the root
    // when something goes wrong, child actors will check with parent actor
    val system: ActorSystem[HelloWorldMain.SayHello] =
      ActorSystem(HelloWorldMain(), "hello")
    system ! HelloWorldMain.SayHello("World")
    system ! HelloWorldMain.SayHello("Akka")
