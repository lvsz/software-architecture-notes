package chapter1.expressionproblem.objectoriented

import DataVariants.*
import Operations.*
import TransformerOperations.*

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
object Combined:
  // shallow mixin composition combines top-level traits
  trait AllOpsForFullLang
      extends PrefixForFullLang
      with InfixForFullLang
      with DoubleOpForFullLang:
    type ExpressionType <: AllOpsExpression

    // deep mixin composition combines inner types of traits
    trait AllOpsExpression
        extends DoubleOpExpression
        with InfixExpression
        with PrefixExpression

    class AllOpsConst(val v: Int)
        extends AllOpsExpression
        with DoubleOpConst
        with InfixConst
        with PrefixConst

    class AllOpsAdd(val l: ExpressionType, val r: ExpressionType)
        extends AllOpsExpression
        with DoubleOpAdd
        with PrefixAdd
        with InfixAdd

    class AllOpsNeg(val t: ExpressionType)
        extends AllOpsExpression
        with DoubleOpNeg
        with PrefixNeg
        with InfixNeg

  object Example extends AllOpsForFullLang:
    type ExpressionType = AllOpsExpression
    def Const(v: Int): ExpressionType = AllOpsConst(v)
    def Add(l: ExpressionType, r: ExpressionType): ExpressionType =
      AllOpsAdd(l, r)
    def Neg(t: ExpressionType): ExpressionType = AllOpsNeg(t)
    def apply() =
      val e = Neg(Add(Const(2), Const(3)))
      val d = e.double()
      println(s"${d.infixNotation} = ${d.prefixNotation} = ${d.eval()}")
