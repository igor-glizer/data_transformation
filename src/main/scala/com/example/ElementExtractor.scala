package com.example

/**
 * Created by Igor_Glizer on 7/24/14.
 */
object ElementExtractor {

  def elements(string: String, splitChar : Char) : Seq[String]  =
  {
    val splitPartialElementsBySplitChar = string.split(splitChar)

    case class ElementsAccumulator(quoteCount : Int = 0, accumulatedElement : String = "", elements : Seq[String] = Nil){
      def accumulate(partialElement: String ) = {
        val quoteCount = this.quoteCount + partialElement.count(isQuote)
        if (quoteCount % 2 == 0)
          completeAccumulationOfElement(partialElement)
        else
          continueAccumulationOfElement(partialElement, quoteCount)
      }
      def completeAccumulationOfElement(curValue : String) = ElementsAccumulator(elements = elements :+ (accumulatedElement + curValue))
      def continueAccumulationOfElement(curValue : String, quoteCount : Int) = ElementsAccumulator(quoteCount, accumulatedElement + curValue + splitChar, elements)

      private def isQuote(c: Char): Boolean =  c == '"'
    }

    splitPartialElementsBySplitChar.foldLeft(ElementsAccumulator())(
      (accumulatorForFields, partialElement) => accumulatorForFields.accumulate(partialElement)).elements
  }
}
