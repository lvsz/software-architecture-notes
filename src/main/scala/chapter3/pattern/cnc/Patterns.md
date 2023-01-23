# Component-and-Connector Patterns

## Broker Pattern

- **Strengths**
  - Easier to add modifications when request go through an intermediary.
  - Ability to seamlessly switch to backup servers can increase redundancy & uptime.
  - Allows for load-balancing server requests.
- **Weaknesses**
  - Extra layer of inderection adds latency, and can turn into a bottleneck.
  - The broker can be a single point of failure.
  - A broker adds up-front complexity.
    - Easy to modify back-end, but hard to modify intermediary, so should be fully functional from the beginning.
  - A broker may be a target for security attacks.
    - Man-in-the-middle attack.
  - A broker may be difficult to test.
  - **MOST importantly**:  
    susceptible to the fallacy of transparent distribution.
    - by disguising remote procedure calls as local ones, the user might not account for any issues associated with communicating over a network, e.g. latency and network failure.
    - cf. REST calls which are unmistakable for local calls when used by developers, and are more likely to get appropriate error and exception handling.

## Model-View-Controller

## Service-Oriented Architecture Pattern

- ## Overview:

  - Computation happens through cooperation of components that provide and/or consume services over a network.

- ## Elements:
  - **Components**:
    - Service providers, provide services through public interfaces, often accompanied by a Service Level Agreement (SLA)
    - Service consumers, invoking services directly or indirectly
    - Registry of services, providers can register their service, consumers can discover services at runtime
    - Orchestration server, coordinates interactions between consumers & providers, based on languages for business processes & workflows
  - **Connectors**:
    - SOAP connector, connector using the Simple Object Access Protocol for synchronous communication between web services, typically over HTTP, ports using it often described in WSDL (Web Service Description Language)
    - REST connector, relies on basic request/reply operations of the HTTP protocol
    - Asynchronous messaging connector, offers point-to-point or publish-subscribe asynchronous message exchanges
- ## Relations:

  - Attachment of the different kinds of components available to the respective connectors

- ## Constraints:

  - Consumers are connected to providers, possibly through an intermediary (Enterprise service bus, registry, orchestration server)
  - Providers may also be consumers
  - ESBs lead to a hub-and-spoke topology
  - Specific SOA patterns impose additional constraints

- ## **Strengths**
  - Allowing interoperability of components distributed on different platforms across the Internet
  - Integrating legacy systems
  - Allowing dynamic configuration
- ## **Weaknesses**
  - Typically complex to build
  - No control over evolution of independent services
  - Performance overhead associated with middleware
  - Services may be performance bottlenecks, often no performance guarantees

## Micro-services

Service-oriented pattern, but what if services weren't just 3rd party, but also used internally. This benefits the agility & scalability of a service, compared to a monolithic approach.

### Micro-services architecture

![micro-service-model][ms1]

- Each service provides and consumes functionality as a mini-application on its own

### REST calls

![micro-service-rest][ms2]

- synchronous request/response cycle of HTTP
- exposes business objects as resources at a URI
- four primary HTTP operation on those resourches:
  - POST
  - GET
  - PUT
  - DELETE
- not prone to fallacy of transparent distribution
  - though blocking calls do require protection against unresponsive services to prevent cascading failures, e.g. in the form a circuit breaker, monitoring how quickly a reponse comes back, breaking the circuit if there's a timeout

### Messaging

- **asynchronicity**
  - sender does not have to wait for the receiver to respond to a message
  - requires a _send-and-forget_ approach
    - request sent contains a `MessageID` and `ReturnAddress`
    - reply sent uses request's `MessageID` as a `CorrelationID`, so that receiver knows what this is a reply to
- **variable timing**
  - messaging system queues up requests until the receiver is ready to process them
  - sender & receiver can produce/consume messages at their own pace

### Containerisation

- service executables and dependencies are packaged into a container image
  - handles deployment dependencies rather than development dependencies
- multiple containers can be spun up from a single image
- previously virtualisation was used, which virtualised an entire hardware system
- containerisation reuses its hosts kernel, making it more efficient
- **infrastructure as code**

- ## Cloud-native applications
  - operate at **global scale**
  - yet remain **responsive**
  - are **resilient** against failures
  - are **elastic** under load variations

## Concurrent Actor Programming

> _An Actor is given by the combination of a `Behavior` and a context in which this behavior is executed._

Actors are self-contained, independent components that communicate by asynchronous messaging.

Actors process messages one-by-one from a mailbox, and only support 3 primitive operations:

- send messages to other actors asynchronously
- create other actors
- change its message processing behaviour

> _In Akka the first capability is accessed by using the `!` or `tell` method on an `ActorRef`, the second is provided by `ActorContext#spawn` and the third is implicit in the signature of `Behavior` in that the next behavior is always returned from the message processing logic._

An actor is effectively **single-threaded**:

- messages are process sequintially
- each message getting processed happens as one atomic unit of execution
- changes in behaviour are delayed until after the actor has finished processing its current messages

But message processors can be **executed concurrently**, i.e. actors are single-thread similar to how thread are single-threaded.

### Actors provide strong encapsulation

- No direct access possible to the actor state
  - state can only be accessed through message sent to known addresses (`ActorRef`s)
- Three ways to obtain an address
  - every actor knows its own address (`self`)
  - actor creation returns an address
  - addresses can be exchanged through messages
    - in previous versions, sender address would get automatically captured
- Actors are completely **independent agents of computation**, more isolated from each other than regular object

  - all actors run fully concurrently to each other
  - messages-passing primitive is asynchronous
    - regular objects can be interruped by the thread scheduler in the middle of a method call
  - no shared data

  ### Actors are by design also suitable for distributed computing

  - strongly encapsulated
  - addresses (`ActorRefs`) are location-transparent
    - local & remote messages both sent using the `!` messages operator
    - fallacy of location transparency not applicable, as messaging is asynchronous regardless, _"fire & forget"_, actors won't block the rest of the system due to network errors

## Publish-Subscribe Pattern

- ## Context
  - There are **independent producers & consumers of data** which must interact.
  - The precise **number and nature** of these are **not predetermined** or fixed, nor is the data they share.
- ## Problem
  - How to create integration mechanisms that allow them to transmit messages while being **unaware of each other's identity or existence**?
- ## Solution
  - Interact via **announced messages**, or **events**.
    - Components may **subscribe** to a set of events.
    - **Publisher** components place events on the bus by **announcing** them.
    - The connector delivers those events to subscriber components that have **registered an interest in those events**.
  - Analog with the _observer pattern_, but on an architectural rather than design level.
- ## **Strengths**
  - Isolates producers from consumers in both space (no need to who or how many) and time (no need to wait for them to process it)
- ## **Weaknesses**
  - Typically increases latency and has a negative effect on scalability & predictability of message delivery time.
    - Bad fit for systems with real-time deadline constraints.
  - Less control over ordering of messages, no guarantee they get delivered.
    - Bad fir for complex interactions where shared state is critical.

[ms1]: ../../../../resources/png/micro-services.png
[ms2]: ../../../../resources/png/micro-service-rest.png
