package com.example

import org.specs2.mutable.SpecificationWithJUnit

/**
 * Created by Igor_Glizer on 7/24/14.
 */
class ElementsExtractorTest extends SpecificationWithJUnit {


  "ElementsExtractor" should {

    "handle string with one element, no qoutes" in {
      ElementExtractor.elements("""a""", ',') must containTheSameElementsAs(Seq("a"))
    }

    "handle string with two elements, no qoutes" in {
      ElementExtractor.elements("""a b""", ' ') must containTheSameElementsAs(Seq("a", "b"))
    }

    "handle string with one qouted element" in {
      ElementExtractor.elements(""""a"""", ' ') must containTheSameElementsAs(Seq(""""a""""))
    }

    "handle string with one composite quoted elements" in {
      ElementExtractor.elements("""a "b c"""", ' ') must containTheSameElementsAs(Seq("a",""""b c""""))
    }

  }
}
