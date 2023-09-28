## Expression Problem
---

### Two dimensions to problem
- datatype defined by a set of data variants
- processes operating on this datatype

### Solution requirements
- extensible in both dimensions
- strong, static type safety
- no modification/duplication of exisiting code
- seperate compilation (no type checking of existing code for compilation
of additions) + independent extensibility (possible to combine
separately developed additions)


### Solution 1: Object-oriented decomposition
- easy to extend datatypes
- operation extensions possible by abstracting over type of data

### Solution 2: Functional decomposition
- uses the visitor pattern
- easy to extend operations
- data extensions possible by abstracting over type Visitor

Extensions in dual dimensions still require some overhead, but are no longer impossible, like in other OO languages
- merging extensions in primary dimensions through *shallow mixin composition*
  * new trait can be declared solely using other traits
  * no need for a body
- merging extensions in secondary dimensions through *deep mixin composition*
  * first the toplevel traits have to be mixed
  * then their subtraits need to be mixed in the body
