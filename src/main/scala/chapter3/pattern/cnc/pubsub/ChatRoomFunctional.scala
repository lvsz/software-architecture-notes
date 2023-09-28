package chapter3.pattern.cnc.pubsub

import akka.{Done, NotUsed}
import akka.actor.typed.{
  ActorRef,
  ActorSystem,
  Behavior,
  DispatcherSelector,
  Terminated
}
import akka.actor.typed.scaladsl.{Behaviors, LoggerOps}

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// LoggerFactory.getLogger(getClass).debug("Starting ActorTestKit")

object ChatRoom {
  sealed trait RoomCommand
  final case class GetSession(
      // name for verification purposes
      screenName: String,
      // prospective clients start out with an ActorRef[GetSession]
      // this actor will receive the SessionEvent reply
      replyTo: ActorRef[SessionEvent]
  ) extends RoomCommand

  sealed trait SessionEvent
  // user gets accepted into room, gets an actor that can post messages
  final case class SessionGranted(handle: ActorRef[PostMessage])
      extends SessionEvent
  // user doesn't get accepted because of `reason`
  final case class SessionDenied(reason: String) extends SessionEvent
  // notification event for when a new message gets posted
  final case class MessagePosted(screenName: String, message: String)
      extends SessionEvent

  sealed trait SessionCommand
  final case class PostMessage(message: String) extends SessionCommand
  private final case class NotifyClient(message: MessagePosted)
      extends SessionCommand

  private final case class PublishSessionMessage(
      screenName: String,
      message: String
  ) extends RoomCommand

  def apply(): Behavior[RoomCommand] =
    chatRoom(List.empty)

  private def chatRoom(
      // a Session is essentially an actor that can receive `SessionCommand`s
      sessions: List[ActorRef[SessionCommand]]
  ): Behavior[RoomCommand] =
    Behaviors.receive { (context, message) =>
      message match {
        case GetSession(screenName, client) =>
          // create a child actor for further interaction with the client
          val ses = context.spawn(
            session(context.self, screenName, client),
            name = URLEncoder.encode(screenName, StandardCharsets.UTF_8.name)
          )
          client ! SessionGranted(ses)
          chatRoom(ses :: sessions)
        case PublishSessionMessage(screenName, message) =>
          val notification = NotifyClient(MessagePosted(screenName, message))
          sessions.foreach(_ ! notification)
          Behaviors.same
      }
    }

  private def session(
      room: ActorRef[PublishSessionMessage],
      screenName: String,
      client: ActorRef[SessionEvent]
  ): Behavior[SessionCommand] =
    Behaviors.receiveMessage {
      case PostMessage(message) =>
        // from client, publish to others via the room
        room ! PublishSessionMessage(screenName, message)
        Behaviors.same
      case NotifyClient(message) =>
        // published from the room
        client ! message
        Behaviors.same
    }
}

object Gabbler {
  import ChatRoom.*

  def apply(): Behavior[SessionEvent] =
    Behaviors.setup { context =>
      Behaviors.receiveMessage {
        // #chatroom-gabbler
        // We document that the compiler warns about the missing handler for `SessionDenied`
        case SessionDenied(reason) =>
          context.log.info("cannot start chat room session: {}", reason)
          Behaviors.stopped
        // #chatroom-gabbler
        case SessionGranted(handle) =>
          handle ! PostMessage("Hello World!")
          Behaviors.same
        case MessagePosted(screenName, message) =>
          context.log.info2(
            "message has been posted by '{}': {}",
            screenName,
            message
          )
          Behaviors.stopped
      }
    }
}
//#chatroom-gabbler

//#chatroom-main
object Example {
  def apply(): Behavior[NotUsed] =
    Behaviors.setup { context =>
      val chatRoom = context.spawn(ChatRoom(), "chatroom")
      val gabblerRef = context.spawn(Gabbler(), "gabbler")
      context.watch(gabblerRef)
      chatRoom ! ChatRoom.GetSession("ol’ Gabbler", gabblerRef)

      Behaviors.receiveSignal { case (_, Terminated(_)) =>
        Behaviors.stopped
      }
    }

  @main
  def runChatRoomFunctional() =
    ActorSystem(Example(), "ChatRoomDemo") //.logConfiguration() // log.info("Starting ChatRoomDemo")
    //val system = ActorSystem(Example(), "ChatRoomDemo")
    //system.log.info("Starting ChatRoomDemo")
    //system
}
//#chatroom-main
