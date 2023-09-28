#### [Software Architectures](../../../../README.md)

# 2. Documenting Architectures

---

## What is software architecture?

> _The architecture of a software system is the **set of structures** needed to **reason about** the system, which comprise **software elements**, **relations** among them, and externally visible **properties** of both._

- A structure is a collection of elements held
  together by relations.
  - Hence several kinds of structures can be produced for a single system.
- No singular structure can claim to be _the_ architecture of the system.
- Each structure enables a **different kind of reasoning** in the design, documentation, and analysis of a system's architecture.

### Are all structures architectural?

**`|A| -> |B|`**

- A calls B
- A is a subtype of B
- A writes to B
- B reads from A
- ...

  **_In order to pass off as an architecture, it is crucial to describe the relations represented between elements._**

- Do not leave functionality and behaviour of corresponding elements up to imagination.
- A structure is architectural if it **supports reasoning** about the **system's properties**.
- Reasoning should be about an attribute important to a **stakeholder**:
  - **functionality** achieved by the system
  - **availability** of the system in the face of faults
  - **difficulty** of making specific changes
  - **responsiveness** to user requests
  - ...

## Software Architecture vs. Code • Simon Brown

[YouTube][url1]

- Focus on abstractions instead of notation
- Software systems are made up out of containers
- A container is something that needs to be running & deployed, hosts data
  - web server
  - web application
  - application server
  - message bus
  - database
  - file system
- Containers are made up out of components
- A component is a bunch of related stuff with a clean interface, something you substitute out
  - classes
  - functions
  - tables
  - views

### The C4 model

1. System Context
   - What are we building?
   - Who is using it?
   - How does it fit into the existing IT environment?
2. Containers
   - What are the high-level tech decisions?
   - How do they communicate with one another?
   - As a dev, where do I need to write the code?
3. Components
   - What components/services is the container made up of?
   - Are the tech choices & responsibilities clear?
4. Classes
   - Assuming OO language.

[url1]: https://youtu.be/GAFZcYlO5S0

## Architectural Views

> _An architectural **view** is a **representation** of an architectural structure according to a template in a chosen notation, and used by some system stakeholders._

### "**Views** and **Beyond**" approach

**Documenting an architecture** is a matter of _documenting the relevant views_ and _adding documentation that applies to more than one view_.

### ![Template for a View][img1]

1. **Primary representation**:
   depicts the elements & relations of the view, using its vocabulary
2. **Element catalog**:
   details at least those elements depicted in the primary representation
3. **Context diagram**:
   shows how the (portion of the) system depicted in this view relates to its environment: humans, other system, physical objects, etc.
4. **Variability guide**:
   lists elements that could be interchanged, e.g. a postgreSQL database could be replaced by a MySQL databas
5. **Rationale**:
   explains why the design refllected in the view came to be, e.g. justification for the choice of a patterns

## Stakeholders

- Architect
  - Negotiating & makes trade-offs among competing requirements & design approaches
  - Providing evidences that the architecture satisfies its requirements
- Analyst
  - Analysing the architecture to ensure that it meets critical quality attribute requirements
  - Often specialised:
    - performance
    - safety
    - security
  - Analysing satisfaction of quality attribute requirements based on a system's architecture
- Business manager
  - Responsible for the functioning of the organisational entity that owns the system
  - Includes executive responsibility, responsibility for defining business processes, ...
  - Understanding the ability of the architecture to meet business goals
- Database admin
  - Responsible for data stores, e.g. db design, data analysis, data modeling, monitoring, etc.
  - Understands how data is created, used, and updated by other architectural elements
- Deployer
  - Responsible for accepting the completed system from the devs and deploying it
  - Understanding the architectural elements that are delivered & installed at the end user's site
- Network admnin
  - Responsible for maintenance & oversight of computer network
  - Determining network loads during various usage scenarios
  - Determinging appropriate network security zoning

TODO see: Conway's law

### Next: [Structures][stc.md]

### Other:

- [Module structures][mod.md]
- [Component-and-connector structures][c&c.md]
- [Allocation structures][all.md]

[stc.md]: ./structures/README.md
[mod.md]: ./structures/Modules.md
[c&c.md]: ./structures/C&C.md
[all.md]: ./structures/Allocation.md
[img1]: ../../resources/png/template-for-a-view.png
