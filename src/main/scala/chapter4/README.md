#### [Software Architectures](../../../../README.md)

# 4. Actor-Based Design Patterns for Micro-Service Architectures

## 4.1 Messaging Patterns

## Request-Response Pattern

> _Include a return address in the message to receive a response._

### Applicability

- Requester already knows the destination address of Responder.
- Addresses can be transported to the Responder in a location-transparent manner; the address of the Requestor is still valid and usable after being transported.

### Considerations

- Requester might need to give the Responder time to recover by backing off when it is overloaded orin a failed state (see Circuit Breaker Pattern).

## Ask Pattern

### Applicability

- A Request-Response cycle needs to be completed before continuing with the logic of the enclosing business transaction.
- Manually correlating responses with requests is cumbersome and error-prone, as it also requires cleaning up stale requests that have been processed or that have timed out.
- Having the requester handle incoming responses for requests pollutes its own protocol, exposing it to the possibility of responding to wrong messages.

![ask-example](../../resources/png/ask-example.png)



## Forward Flow Pattern

> _Let the information and the messages flow directly toward their
destination where possible._

![forward-flow-example](../../resources/png/forward-flow-example.png)

### Applicability

- Optimize overall resource consumption or response-latency by **identifying shortcuts** in **message response** flow.
- Comes at the cost of **introducing coupling** between source and target services (between file server and client), as reply-to addresses need to be shared by the intermediary (router).

## Aggregator Pattern

> _Create an ephemeral component if multiple service responses are needed
to compute the result for a service call._

### Applicability

- Multiple Request-Response cycles need to be completed.
- None of the requests depends on the responses of the others, several cycles can therefore be made in parallel

![aggregator-example](../../resources/png/aggregator-example.png)

#### Example use case: creating a page using a builder[^1] , aggregating each component using actors retrieving  data in parallel.

[^1]: design pattern that allows creating of complex structures before all constructor arguments are available