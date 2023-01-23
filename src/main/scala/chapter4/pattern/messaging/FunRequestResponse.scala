package chapter4.pattern.messaging

import akka.NotUsed
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Terminated}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import org.slf4j.{Logger, LoggerFactory}
// import chapter4.pattern.messaging.{Request, Response}

// a requestor is an actor that receives responses
object FunRequester:
  def apply(responder: ActorRef[Request]): Behavior[Response] =
    Behaviors.setup { context =>
      responder ! Request("fun hello", context.self)
      Behaviors.receiveMessage { response =>
        response match {
          case Response(result) =>
            println("got fun: " + result)
            Behaviors.same
        }
      }
    }

// a responder is an actor that receives requests
object FunResponder:
  def apply(): Behavior[Request] =
    Behaviors.receiveMessage { message =>
      message match {
        case Request(request, replyTo) =>
          println("got fun request: " + request)
          replyTo ! Response("got fun!")
          Behaviors.same
      }
    }

object FunExample:
  def apply(): Behavior[NotUsed] =
    Behaviors.setup { context =>
      val responder = context.spawn(FunResponder(), "responder")
      val requester = context.spawn(FunRequester(responder), "requester")
      val ooRequester = context.spawn(Requester(responder), "ooRequester")
      Behaviors.empty
    }
  @main
  def runFunRequestResponse() =
    LoggerFactory
      .getLogger(Logger.ROOT_LOGGER_NAME)
      .debug("prevent slf4j noise")
    ActorSystem(FunExample(), "FunRequestResponseExample")
