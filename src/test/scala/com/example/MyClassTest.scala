package com.example

import org.specs2.mutable.{After, SpecificationWithJUnit}
import  org.specs2.specification.Scope

/**
 * Created by Igor_Glizer on 7/20/14.
 */
class MyClassTest extends SpecificationWithJUnit {

  trait Context extends Scope{

    val myClass = new MyClass
  }

  trait ExtendedContext extends Context{

    val goodbyeLength = myClass.goodbye.length
  }

  trait ContextWithCleanup extends Context with After{
    override def after: Any = print("after")
  }

  "MyClass" should {

    "say hello" in new ContextWithCleanup  {
      myClass.hello must_== "hello world"
    }

    "say goodbye" in new ExtendedContext  {
      myClass.goodbye must_== "goodbye"
      goodbyeLength must_== 7
    }

  }

}
