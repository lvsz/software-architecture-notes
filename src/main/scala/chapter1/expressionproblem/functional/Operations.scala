package chapter1.expressionproblem.functional

import DataVariants.*

object Operations:
  trait DoubleOpForFullLang extends FullLang:
    class DoubleVisitor extends FullVisitor[Expression]:
      this: VisitorType[Expression] =>
      def visit(e: Const): Expression = Const(2 * e.v)
      def visit(e: Add): Expression = Add(e.l.accept(this), e.r.accept(this))
      def visit(e: Neg): Expression = Neg(e.t.accept(this))

  trait PrefixOpForFullLang extends FullLang:
    class PrefixVisitor extends FullVisitor[String]:
      this: VisitorType[String] =>
      private def prefixer(op: String, args: Expression *) =
        s"(${op}${args.map(_.accept(this)).fold("")(_ + " " + _)})"
      def visit(e: Const): String = e.toString
      def visit(e: Add): String =
        prefixer("+", e.l, e.r)
      def visit(e: Neg): String = prefixer("-", e.t)

  // shallow mixin composition to combine independent operation extensions
  // cf. deep mixin composition required for OO decomposition
  trait AllOpsForFullLang extends DoubleOpForFullLang with PrefixOpForFullLang

  object Example extends AllOpsForFullLang:
    type VisitorType[T] = FullVisitor[T]
    def apply() =
      val e: Expression = Neg(Add(Neg(Const(5)), Const(3)))
      println(e.accept(new DoubleVisitor).accept(new PrefixVisitor))
