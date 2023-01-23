package chapter4.pattern.messaging

import akka.NotUsed
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Terminated}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import org.slf4j.{Logger, LoggerFactory}

implicit object Protocol:
  case class Request(req: String, replyTo: ActorRef[Response])
  case class Response(result: String)

/** Requester actor
 * requester receives a Response as context
 */
class Requester(
    context: ActorContext[Protocol.Response],
    responder: ActorRef[Protocol.Request]
) extends AbstractBehavior[Protocol.Response](context):
  import Protocol._
  responder ! Request("hello", context.self)
  override def onMessage(message: Response): Behavior[Response] =
    message match {
      case Response(result) =>
        println("got response: " + result)
        Behaviors.same
    }

object Requester:
  import Protocol.*
  // requester knows address of responder in advance
  def apply(responder: ActorRef[Request]): Behavior[Response] =
    Behaviors.setup(context => new Requester(context, responder))

class Responder(context: ActorContext[Protocol.Request])
    extends AbstractBehavior[Protocol.Request](context):
  import Protocol.*
  override def onMessage(message: Request): Behavior[Request] =
    message match {
      case Request(req: String, replyTo: ActorRef[Response]) =>
        println("got request: " + req)
        replyTo ! Response("got it!")
        Behaviors.same
    }

object Responder:
  def apply(): Behavior[Protocol.Request] =
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
    LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME).debug("prevent slf4j noise")
    ActorSystem(Example(), "RequestResponseExample")
