# Component-and-connector structures
##
### Elements
- Components:
  * principal processing units and data stores  
  * have a set of ports through which it interacts with other components
- Connectors:
  * pathways of interaction between components
  * have a set of roles that indicate how they may be used in interactions by components
### Relations
- Attachments:
  * component ports are associated with connector roles
  * yields a graph of components and connectors
- Interface delegation:
  * certain component ports can be associated with one or more ports in an _internal subarchitecture_
  * similar situations can occur for the roles of a connector
### Constraints
- Components can be attached only to connectors.
- Connectors can be attached only to components.
- Attachments can be made only between compatible ports and roles.
- Interface delegation can be defined only between two compatible ports (or two compatible roles).
- Every connector must be attached to a component.

## Documenting c&c properties
Component & connector views are useful to document everything related to runtime behaviour, as well as the properties of that behaviour.

- Reliability
  * What is the likelihood of failure for a given component or connector?
- Performance
  * What kinds of response times will the component provide under what loads?
  * What kinds of latencies and throughputs can be expected for a given connector?
- Resource requirements
  * What are the processing and storage needs of a component or a connector?
- Functionality
  * What functions does an element perform?
- Security
  * Does a component or a connector enforce or provide security features, such as encryption, audit trails, or authentication?
- Concurrency
  * Does this component execute as a separate process or thread?
- Tier
  * For a tiered topology, what tier does the component reside in?
  * This property helps to define the build and deployment procedures, as well as platform requirements for each tier.

![C&C view key][img2]

When using a non-standardised diagram for a view, it is crucial add a key, denoting the semantics of the diagram.


## Example uses of c&c views
### Communication
- Component-and-connector views help answer questions such as:
  * What are the major executing components and how do they interact at runtime?
  * What are the major shared data stores?
  * **Which parts of the system are replicated?**
  * How does data progress through the system?
  * **Which parts of the system can run in parallel?**
  * **Can the system's structure change as it executes and, if so, how?**
### Analysis
- Component-and-connector views are crucial to reasoning about the **system's runtime properties** such as performance, security, availability, etc.

## Next: [Allocation structures](Allocation.md)
## Other:
- [Overview][ch2.md]
- [Structures][stc.md]
- [Modules][mod.md]

[ch2.md]: ../README.md
[stc.md]: ./README.md
[mod.md]: ./Modules.md
[all.md]: ./Allocation.md

[img2]: ../../../resources/png/c-and-c-view-key.png