package chapter1.expressionproblem.objectoriented

import DataVariants.*
import java.beans.Expression

/** Transformer operations return data variants
  */
object TransformerOperations:
  trait DoubleOpForFullLang extends FullLang:
    type ExpressionType <: DoubleOpExpression
    trait DoubleOpExpression extends Expression:
      def double(): ExpressionType

    trait DoubleOpConst extends Const with DoubleOpExpression:
      def double() = Const(v * 2)
    trait DoubleOpAdd extends Add with DoubleOpExpression:
      def double() = Add(l.double(), r.double())
    trait DoubleOpNeg extends Neg with DoubleOpExpression:
      def double() = Neg(t.double())

    // abstract factory methods, to be implemented at point
    // where ExpressionType abstract type is closed
    def Const(v: Int): ExpressionType
    def Add(l: ExpressionType, r: ExpressionType): ExpressionType
    def Neg(t: ExpressionType): ExpressionType

  object Example extends DoubleOpForFullLang:
    // closing of abstract type
    type ExpressionType = DoubleOpExpression

    // implementation of factory methods
    case class C(v: Int) extends DoubleOpConst
    case class A(l: ExpressionType, r: ExpressionType) extends DoubleOpAdd
    case class N(t: ExpressionType) extends DoubleOpNeg

    def Const(v: Int): ExpressionType = C(v)
    def Add(l: ExpressionType, r: ExpressionType): ExpressionType = A(l, r)
    def Neg(t: ExpressionType): ExpressionType = N(t)

    def apply() =
      val e = N( N(A(C(6), C(7))) )
      println(e.double().eval())
