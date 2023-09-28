package chapter4.pattern.messaging

import akka.actor.typed.ActorRef

case class Request(req: String, replyTo: ActorRef[Response])
case class Response(result: String)
