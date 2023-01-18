package chapter1.expressionproblem.objectoriented

import DataVariants.*
import Operations.*

/** Expression Problem 2 dimensions to problem:
  *   - datatype defined by a set of data variants
  *   - processes operating on this datatype Solution requirements:
  *   - extensible in both dimensions
  *   - strong, static type safety
  *   - no modification/duplication of exisiting code
  *   - seperate compilation (no type checking of existing code for compilation
  *     of additions) + independent extensibility (possible to combine
  *     separately developed additions)
  */

/** Solution 1: Object-oriented decomposition
  *   - easy to extend datatypes
  *   - operation extensions possible by abstracting over type of data
  */
object Extra:
  trait SubLang extends BaseLang:
    trait Sub extends Expression:
      val l: ExpressionType
      val r: ExpressionType
      def eval() = l.eval() - r.eval()

  trait MultLang extends BaseLang:
    trait Mult extends Expression:
      val l: ExpressionType
      val r: ExpressionType
      def eval() = l.eval() * r.eval()

  trait DivLang extends BaseLang:
    trait Div extends Expression:
      val l: ExpressionType
      val r: ExpressionType
      def eval() = l.eval() / r.eval()

  trait ConstLang extends BaseLang:
    trait Const extends Expression:
      val v: Int
      def eval() = v

  trait ExtraLang extends FullLang with SubLang with MultLang with DivLang

  trait PrefixForExtraLang extends PrefixForFullLang with ExtraLang:
    trait PrefixSub extends Sub with PrefixExpression:
      def prefixNotation: String = prefixer("-", l, r)
        //s"(- ${l.prefixNotation} ${r.prefixNotation})"
    trait PrefixMult extends Mult with PrefixExpression:
      def prefixNotation: String = prefixer("*", l, r)
    trait PrefixDiv extends Div with PrefixExpression:
      def prefixNotation: String = prefixer("/", l, r)

  object Example extends ExtraLang with PrefixForExtraLang:
    type ExpressionType = PrefixExpression
    case class C(v: Int) extends PrefixConst
    case class A(l: ExpressionType, r: ExpressionType) extends PrefixAdd
    case class N(t: ExpressionType) extends PrefixNeg
    case class S(l: ExpressionType, r: ExpressionType) extends PrefixSub
    case class M(l: ExpressionType, r: ExpressionType) extends PrefixMult
    case class D(l: ExpressionType, r: ExpressionType) extends PrefixDiv
    def apply() =
      val e = M(D(C(6), C(2)), S(N(A(C(1), C(2))), C(7)))
      println(e.prefixNotation + " = " + e.eval())
