# 3. Architectural Patterns
Architectural patterns establish a relation between:
- **a context**
  * recurring, real-word situation
- **a problem**
  * the problem, **_appropriately generalized_**, that arises from the context
- **a solution**
  * a **successful architectural resolution** to the problem, **_appropriately abstracted_**.
  * determined and described by:
    - a set of element types
    - a set of interaction mechanisms or connectors
    - a topological layout of the components
    - a set of semantic constraints covering topology, element behaviour, and interaction mechanisms

## Distributed systems

### Resilience against delivery failures

Rendering messages first-class entities enables implementing delivery guarantees:

- **at-most-once delivery**:
  - default strategy for Akka
  - no state required at sender nor receiver
  - message will be delivered [0,1] times
- **at-least-once delivery**:
  - keep state at sender to ensure message will be resent until acknowledged by recipient
  - will most likely require a timer
  - simply checking whether message arrived in the mailbox is inadequate, errors may still occur afterwards
    - each implementation has different risks & needs, so default implementation can be given by a framework
  - message will be delivered [1,∞] times, as acknowledgement messages may get lost
- **exactly-once delivery**:
  - like previous, but additional requirement for receiver to make sure only first delivery gets processed
  - if receiver gets a message it already processed & acknowledged, assume acknowledgement message got lost, and simply resend acknowledgement message
  - message will be delivered exactly 1 time
    - assuming eventual availability of a channel between sender & recipient