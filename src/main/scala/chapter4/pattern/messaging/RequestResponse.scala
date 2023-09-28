package chapter4.pattern.messaging

import akka.NotUsed
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Terminated}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import org.slf4j.{Logger, LoggerFactory}
// import chapter4.pattern.messaging.{Request, Response}

/** Requester actor, implemented by extending AbstractBehavior, which requires
  * overriding the onMessage method, and passing its context as a construction
  * parameter. Subclasses of AbstractBehavior need to be created through
  * Behaviors.setup. ActorContext provides access to its own identity.
  */
class Requester(
    context: ActorContext[Response],
    responder: ActorRef[Request]
) extends AbstractBehavior[Response](context):
  responder ! Request("hello", context.self)
  // process an incoming message and return the next behavior (unchanged here)
  override def onMessage(message: Response): Behavior[Response] =
    message match {
      case Response(result) =>
        println("got response: " + result)
        Behaviors.same
    }

// Companion object, creating an Requester actor if given a Responder actor
object Requester:
  // requester knows address of responder in advance
  def apply(responder: ActorRef[Request]): Behavior[Response] =
    Behaviors.setup(context => new Requester(context, responder))

/** Responder actor
  */
class Responder(context: ActorContext[Request])
    extends AbstractBehavior[Request](context):
  override def onMessage(message: Request): Behavior[Request] =
    message match {
      case Request(req: String, replyTo: ActorRef[Response]) =>
        println("got request: " + req)
        replyTo ! Response("got it!")
        Behaviors.same
    }

object Responder:
  def apply(): Behavior[Request] =
    Behaviors.setup(context => new Responder(context))

object Example:
  def apply(): Behavior[NotUsed] =
    Behaviors.setup { context =>
      val responder = context.spawn(Responder(), "responder")
      val requester = context.spawn(Requester(responder), "requester")
      Behaviors.empty
    }
  @main
  def runRequestResponse() =
    LoggerFactory
      .getLogger(Logger.ROOT_LOGGER_NAME)
      .debug("prevent slf4j noise")
    ActorSystem(Example(), "RequestResponseExample")
