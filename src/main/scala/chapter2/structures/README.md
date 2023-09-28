## Categories of architectural structures and their views
- [**Module structures**][mod.md]
  * show how a system is to be structured as a set of **code or data units** (modules) that have to be constructed or procured
  * static structures
  * focuses on the structure & implementation of the software: packages, classes, functions, etc.
    - e.g. a single `client` class
- [**Component-and-connector structures**][c&c.md]
  * show how a system is structured as a set of **code or data units** (modules) that have to be constructed or procured
  * dynamic structures
  * focuses on runtime configuration, interaction and dependencies between elements
    - e.g. multiple `client` instances
- [**Allocation structures**][all.md]
  * show how the system will **relate to non-software structures in its environment**
  * this can include:
    - servers
    - CPUs
    - file systems
    - networks
    - dev teams
  * _"Where are we going to run our components?"_


### Modules vs. Components
Generally, modules & components have a many-to-many relation, i.e. one or more modules can correspond to one or more components, and a one or more components can correspond to one or more module.

#### **Modules**:
- A module is an **implementation _unit_** that provides a coherent set of responsibilities.
- A **responsibility** is a general statement about an architecture element.
- Responsibilities include:
  * actions to perform
  * knowledge to maintain
  * decisions to make
  * the role it plays in achieving the system's overall quality attributes or functionality
- Focus on static, interchangable units in a structure's design

#### **Components**:
- Components are the **principal computational _elements_** and data stores that execute in a sytem.
- Components **interact** with each other at **runtime** to carry out the system's function,
- Focus on dynamic, connected elements in a structure's operation

### Next: [Module structures][mod.md]
### Other:
- [Overview][ch2.md]
- [Component-and-connector structures][c&c.md]
- [Allocation structures][all.md]

[ch2.md]: ../README.md
[mod.md]: ./Modules.md
[c&c.md]: ./C&C.md
[all.md]: ./Allocation.md