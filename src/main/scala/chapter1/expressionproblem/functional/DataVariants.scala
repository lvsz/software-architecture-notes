package chapter1.expressionproblem.functional

object DataVariants:
  trait BaseLang:
    // generic abstract type keeps expression hierarchy
    // open for operation extensions
    type VisitorType[T] <: Visitor[T]
    // Visitor[T] is supertype of VisitorType
    trait Visitor[T]
    // generic abstract method, parametrized in its return type T
    trait Expression:
      def accept[T](v: VisitorType[T]): T

  trait ConstLang extends BaseLang:
    case class Const(v: Int) extends Expression:
      def accept[T](v: VisitorType[T]): T =
        v.visit(this)
    // ConstVisitor[T] is a supertype of VisitorType
    type VisitorType[T] <: ConstVisitor[T]
    // double dispatch to a visit method
    // i.e. the concrete method called depends on runtime type of 2 objects
    trait ConstVisitor[T] extends Visitor[T]:
      def visit(e: Const): T
    trait ConstEvalVisitor extends ConstVisitor[Int]:
      def visit(e: Const): Int = e.v

  trait AddLang extends BaseLang:
    // every new data variant requires an accept method
    case class Add(l: Expression, r: Expression) extends Expression:
      // on accept, call on the given argument v to visit this data variant
      def accept[T](v: VisitorType[T]): T = v.visit(this)
    type VisitorType[T] <: AddVisitor[T]
    trait AddVisitor[T] extends Visitor[T]:
      def visit(e: Add): T
    trait AddEvalVisitor extends AddVisitor[Int]:
      // self type required as AddEvalVisitor & AddVisitor are
      // supertypes of VisitorType, not subtypes
      this: VisitorType[Int] =>
      def visit(e: Add): Int = e.l.accept(this) + e.r.accept(this)

  trait NegLang extends BaseLang:
    case class Neg(t: Expression) extends Expression:
      def accept[T](v: VisitorType[T]): T = v.visit(this)
    type VisitorType[T] <: NegVisitor[T]
    trait NegVisitor[T] extends Visitor[T]:
      def visit(e: Neg): T
    trait NegEvalVisitor extends NegVisitor[Int]:
      // self type required as AddEvalVisitor & AddVisitor are
      // supertypes of VisitorType, not subtypes
      this: VisitorType[Int] =>
      def visit(e: Neg): Int = -e.t.accept(this)

  trait FullLang extends BaseLang with ConstLang with AddLang with NegLang:
    type VisitorType[T] <: FullVisitor[T]
    // deep mixin composition for independent data extensions
    // cf. OO decomposition requiring deep mixin only for operation extensions
    trait FullVisitor[T]
        extends Visitor[T]
        with ConstVisitor[T]
        with AddVisitor[T]
        with NegVisitor[T]
    class FullEvalVisitor
        extends FullVisitor[Int]
        with ConstEvalVisitor
        with AddEvalVisitor
        with NegEvalVisitor:
      this: VisitorType[Int] =>

  object Example extends FullLang:
    type VisitorType[T] = FullVisitor[T]
    def apply() =
      val e = Neg(Add(Neg(Const(5)), Const(3)))
      println(e.accept(new FullEvalVisitor))
