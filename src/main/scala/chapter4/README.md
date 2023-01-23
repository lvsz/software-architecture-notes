#### [Software Architectures](../../../../README.md)

# 4. Actor-Based Design Patterns for Micro-Service Architectures

## 4.1 Messaging Patterns

### Request-Response Pattern

> _Include a return address in the message to receive a response._

#### Applicability

- Requester already knows the destination address of Responder.
- Addresses can be transported to the Responder in a location-transparent manner; the address of the Requestor is still valid and usable after being transported.

#### Considerations

- Requester might need to give the Responder time to recover by backing off when it is overloaded orin a failed state (see Circuit Breaker Pattern).
