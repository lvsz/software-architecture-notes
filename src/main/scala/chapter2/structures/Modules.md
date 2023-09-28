[Chapter 2: Structures](README.md)

# Module structures

### Elements

- Modules:
  - implementation units that provide a coherent set of responsibilities

### Relations

- is-a-part-of:
  - part/whole relationship
  - between submodule & aggregate module
- depends-on:
  - dependency relationship between modules
  - specific module views elaborate on the nature of the dependency
- is-a:
  - generalisation/specialisation relationship
  - between a child module (more specialised) and a parent module (more general)
- ...

### Constraints

- Specific module views may impose particular topological constraints
  - e.g. limitations on the visibility between modules

## Documenting module properties

- Name
  - can denote module's role
  - can reflect position in decomposition hierarchy
- Mapping to source code units
  - e.g. module Account: IAccount.scala, AccountImpl.scala, AccountOrmMapping.xml, ...
- Visibility of interfaces
  - submodules can be **encapsulated**, their interfaces are only used by the submodules in the enclosing parent module
  - submodules can be **selectively exposed**, with the parent module exposing a subset of its submodules' interfaces

## Example uses of module views

### Construction

- Blueprint for source code
  - using views that map modules closely to physical structures like directories
- Planning incremental development
  - using views that show dependencies between modules
- Defining work assignments, implementation schedules, budget estimates, etc.

### Analysis

- Change-impact analysisa, e.g. if this module changes, on which modules could this have an impact
  - using views that show dependencies between modules
- Requirements traceability analysis
  - determine which functional requirements are met by which module responsibilities

### Communication

- Communicating the functionality of a system, the structure of its code base, and the data it manages
  - using views at various levels of granularity of module decomposition

## Next: [Component-and-connector structures][c&c.md]

## Other

- [Overview][ch2.md]
- [Structures][stc.md]
- [Component-and-connector structures][c&c.md]
- [Allocation structures][all.md]

[ch2.md]: ../README.md
[stc.md]: ./README.md
[c&c.md]: ./C&C.md
[all.md]: ./Allocation.md
