# Component-and-Connector Patterns

## Broker Pattern

- **Strengths**
  * Easier to add modifications when request go through an intermediary.
  * Ability to seamlessly switch to backup servers can increase redundancy & uptime.
  * Allows for load-balancing server requests.
- **Weaknesses**
  * Extra layer of inderection adds latency, and can turn into a bottleneck.
  * The broker can be a single point of failure.
  * A broker adds up-front complexity.
    - Easy to modify back-end, but hard to modify intermediary, so should be fully functional from the beginning.
  * A broker may be a target for security attacks.
    - Man-in-the-middle attack.
  * A broker may be difficult to test.
  * **MOST importantly**:  
    susceptible to the fallacy of transparent distribution.
    - by disguising remote procedure calls as local ones, the user might not account for any issues associated with communicating over a network, e.g. latency and network failure.
    - cf. REST calls which are unmistakable for local calls when used by developers, and are more likely to get appropriate error and exception handling.

## Concurrent Actor Programming

Actors are self-contained, independent components that communicate by asynchronous messaging.

Actors process messages one-by-one from a mailbox, and only support 3 primitive operations:

- change its message processing behaviour
- send messages to other actors asynchronously
- create other actors

An actor is effectively **single-threaded**
-  messages are process sequintially
- each message getting processed happens as one atomic unit of execution
- changes in behaviour are delayed until after the actor has finished processing its current messages

But message processors can be **executed concurrently**, i.e. actors are single-thread similar to how thread are single-threaded.

Actors provide strong encapsulation

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
  
  Actors are by design also suitable for distributed computing
  - strongly encapsulated
  - addresses (`ActorRefs`) are location-transparent
    - local & remote messages both sent using the `!` messages operator
    - fallacy of location transparency not applicable, as messaging is asynchronous regardless, _"fire & forget"_, actors won't block the rest of the system due to network errors
