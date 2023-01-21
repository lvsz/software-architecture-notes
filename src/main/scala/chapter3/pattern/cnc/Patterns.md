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
        