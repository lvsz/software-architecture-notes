# 3.1. Module Patterns
## Layer Pattern
TODO
### Context
-
### Problem
-
### Solution
- To achieve this separation of concerns, the layered pattern divides the software into units called layers.
- Each layer is a grouping of modules that offers a cohesive set of services.
  * e.g. no grouping of operating system modules along with web service modules.
- The **usage must be unidirectional**.
- Layers completely partition a set of software, **each partition exposed through a public interface**.

## Layer Pattern Solution
### Overview
-
### Elements
- Layer, a kind of module.
  The description of a layer should define what modules the layer contains.
### Relations
-
### Constraints
  * When viewed using a Dependency Structure Matrix, with modules sorted by layer, from top to bottom, it should only show dependencies below the diagonal, otherwise it violates the unidiractional usage constraint.
---
### **Strengths**
- Promotes modifiability, reuse, portability.
  * e.g. module internals can be freely modified or interchanged, as long as its public interface remains the same.
- Achieves separation of concerns.
  * e.g. user interface module and database access module are completely seperate, and can be modified independently.
- Manages complexity and facilitates communication of code structure to developers.
### **Weaknesses**
- The addition of layers adds up-front cost and complexity to a system.
- Layers contribute a performance penalty.
---


## Dependency Inversion: from Layer to Onion

Traditional procedural interpretation:
call into a lower layer using the interface declared there.
- dependency direction is _downwards_ **from high-level layers at the top to low-level layers at the bottom**
- high-level layers (e.g. for business logic) depend on low-level utilitarian layers (e.g. for persistence)
- **changes to the lower-level layers require running integration tests again**

  ![layers](../../resources/svg/module-layers.svg)

- API Layer:
  * public interface to the domain
- Domain Layer:
  * domain model implementation
- Infrastructure Layer:
  * frameworks, integration with other services, etc.

### OO implementation with dependency inversion:
High-level layer declares interface implemented by low-level layer

![onion](../../resources/svg/module-onion.svg)
- coupling direction is **inwards**:
  from outer low-level layer to inner high-level layer
- outer layers depend on inner layers,
  as **_they implement the interfaces declared there_**
- **changes to outer layers do not affect inner layers,**
 **the inner layers do not know about the outer layers**
- **inner layers can be compiled, run, and tested separately from the infrastructure layers**
  * e.g. swap an outer layer for a mock when testing an inner layer

## Aspect-Oriented Pattern
### Overview
-
### Elements
-
### Relations
-
### Constraints
-
---
### **Strengths**
-
### **Weaknesses**
-
---

### Modularising cross-cutting concerns
AOP provides constructs for factoring out cross-cutting concerns from modules into a seperate module called an **aspect** that consists of:
- **Advice**
  * contains the code from the cross-cutting concern
- **Pointcut expression**
  * specifies the **join points** in the base program where base and cross-cutting concerns should be composed
  * join points are specific to each AOP language, according to its join point model
  * e.g. method invocation, method execution, instance creation, error throwing, etc.

Two main categories of _pointcut languages_:
- Extensional
  * Enumerate as a dev every join point in the code that the advice code should be combined with, e.g. using annotations.
  * Think {0, 2, 4, ...}
  * Spring AOP still allows this
- Intentional
  * Use expressions to indicate which join points to look at.
  * Think {n ∈ ℕ | n is even}

#### Conventional logging:
![conventional logging](../../resources/svg/conventional-logging.svg)

#### Aspect-oriented logging:
![aspect-oriented logging](../../resources/svg/aop-logging.svg)  
Tracing aspect inserts logging functionality through means of _advice code_.

> _"ThERe aRe OnlY LiKE fiVE CaNONiCaL asPecTs:"_
- Transaction management
- Exception handling
- Logging
> _"WhAt otHEr AspECtS aRe THerE!"_

AOP possible in Scala using only built-in functionality. Possible because classes can mix in multiple traits, and the use of `super` in traits is late-bound.

- **Overview**
  * The aspect-oriented pattern factors out cross-cutting concerns into modules called aspects.
- **Elements**
  * **Aspect**, a kind of module that contains the implementation of a cross-cutting concern.
  * Aspects modify the behaviour of the base code by composing it with **advice code** at _join points_ specified by a **pointcut expression**.
- **Relations**
  * **Cross-cuts**, the relation being an aspect module to a module that will be affected by the cross-cutting logic of the aspect.
- **Constraints**
  * An aspect can crosscut one ore more regular modules as well as other aspect modules.
  * An aspect that crosscuts itself may cause infinite recursion, depending on the implementation.
- **_Strengths_**
  * Modularizes cross-cutting concerns.
  * Supports comprehension and modifiability.
- **_Weaknesses_**
  * Little language support.
    - Used to be more popular.
  * Prone to fragility problems: pointcuts and advice make many implicit assumptions about the modules into which they cross-cut.
    - Pointcut expression might get accidentally placed, e.g. if they look at certain suffixes.
    - Advice code might get composed with methods unaware this could happen.
    - Having a part of the code base affect the behaviour of other modules in possibly unexpected ways can make it hard to maintain.
    - cf. fragile base class problem, where subclasses might get broken by assumption made about their super class.
