package chapter1.expressionproblem.objectoriented

import DataVariants.*

/** Operation extensions */
object Operations:
  trait PrefixOp extends BaseLang:
    type ExpressionType <: PrefixExpression
    trait PrefixExpression extends Expression:
      def prefixNotation: String
    def prefixer(op: String, args: PrefixExpression *) =
      s"(${op}${args.map(_.prefixNotation).fold("")(_ + " " + _)})"

  // all data variants need to be subclassed so that they
  // can include an implementation of the new operation
  trait PrefixForConst extends ConstLang with PrefixOp:
    trait PrefixConst extends Const with PrefixExpression:
      def prefixNotation: String = v.toString

  trait PrefixForAdd extends AddLang with PrefixOp:
    trait PrefixAdd extends Add with PrefixExpression:
      def prefixNotation: String = prefixer("+", l, r)
        //"(+ %s %s)".format(l.prefixNotation, r.prefixNotation)

  trait PrefixForNeg extends NegLang with PrefixOp:
    trait PrefixNeg extends Neg with PrefixExpression:
      def prefixNotation: String = prefixer("-", t)
        //"(- %s)".format(t.prefixNotation)

  // mixin to combine the operation extensions for each data extensions
  trait PrefixForFullLang
      extends PrefixOp
      with PrefixForConst
      with PrefixForAdd
      with PrefixForNeg

  trait InfixOp extends BaseLang:
    type ExpressionType <: InfixExpression
    trait InfixExpression extends Expression:
      def infixNotation: String
  
  trait InfixForFullLang extends FullLang with InfixOp:
    trait InfixConst extends Const with InfixExpression:
      def infixNotation: String = v.toString
    trait InfixAdd extends Add with InfixExpression:
      def infixNotation: String = l.infixNotation + " + " + r.infixNotation
    trait InfixNeg extends Neg with InfixExpression:
      def infixNotation: String = s"-(${t.infixNotation})"

  object PrefixExample extends PrefixForFullLang:
    type ExpressionType = PrefixExpression
    // close open definition using case classes:
    // closed for modification, open for extension
    case class C(v: Int) extends PrefixConst
    case class A(l: ExpressionType, r: ExpressionType) extends PrefixAdd
    case class N(t: ExpressionType) extends PrefixNeg
    def apply() =
      val e = N(A(C(6), C(7)))
      println(e.prefixNotation + " = " + e.eval())

  object InfixExample extends InfixForFullLang:
    type ExpressionType = InfixExpression
    case class C(v: Int) extends InfixConst
    case class A(l: ExpressionType, r: ExpressionType) extends InfixAdd
    case class N(t: ExpressionType) extends InfixNeg
    def apply() =
      val e = N(A(C(6), C(7)))
      println(e.infixNotation + " = " + e.eval())