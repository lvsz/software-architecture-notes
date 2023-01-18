package chapter1.expressionproblem.objectoriented

object DataVariants:
  // wrapper for Expression and ExpressionType types
  trait BaseLang:
    // ExpressionType is abstract to keep data variants open for extension
    type ExpressionType <: Expression
    trait Expression:
      def eval(): Int

  trait ConstLang extends BaseLang:
    trait Const extends Expression:
      val v: Int
      def eval() = v

  trait AddLang extends BaseLang:
    trait Add extends Expression:
      val l: ExpressionType
      val r: ExpressionType
      def eval() = l.eval() + r.eval()

  trait NegLang extends BaseLang:
    trait Neg extends Expression:
      val t: ExpressionType
      def eval() = -t.eval()

  // mixin composition for combining independent extenstions
  trait FullLang extends BaseLang with ConstLang with AddLang with NegLang

  object Example extends FullLang:
    case class C(v: Int) extends Const
    case class A(l: ExpressionType, r: ExpressionType) extends Add
    case class N(t: ExpressionType) extends Neg
    type ExpressionType = Expression
    def apply() =
      val e: Expression = N(A(C(1), C(2)))
      println(e.eval())
