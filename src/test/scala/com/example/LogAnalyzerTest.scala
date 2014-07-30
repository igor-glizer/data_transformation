package com.example

import org.specs2.mutable.{SpecificationWithJUnit, Specification}
import org.specs2.specification.Scope

/**
 * Created by Igor_Glizer on 7/29/14.
 */
class LogAnalyzerTest extends SpecificationWithJUnit {

  trait Context extends Scope
  {
    val l1 = """166.147.120.17 -  -  [18/Jul/2014:00:00:01 +0000] "GET / HTTP/1.1" 200 41371 "http://lm.facebook.com/l.php?u=http%3A%2F%2Fwww.hwhn.org%2F&h=BAQHjyUsT&enc=AZOT_bGPzQxJmJiWm8_k6KPsRRq6mx1oca5ceGzzgGKEHXOTzua34sO38MSzR2ktntcPeLeuG6dnfALyMEDyRhR_a9J46c7PXb-HTWCBe9dSQw9DKrHGcOzUWDy8GKkeqLVdKmnjbHJcTpYGTF7iMFx2&s=1" "Mozilla/5.0 (Linux; U; Android 4.3; en-us; SAMSUNG-SGH-I747 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30""""
    val l2 = """24.185.144.104 -  -  [18/Jul/2014:00:00:02 +0000] "GET /_api/dynamicmodel HTTP/1.1" 200 12988 "http://www.heavenberry.com/" "Mozilla/5.0 (Linux; Android 4.4.2; SM-G900V Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.141 Mobile Safari/537.36""""
    val l3 = """38.123.140.114 -  -  [18/Jul/2014:00:00:03 +0000] "GET /cgi-bin/wspd_cgi.sh/WService=wsbroker1/webtools/oscommand.w HTTP/1.1" 400 0 "-" "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)""""
    val requests = LogParser.parse(List(l1,l2,l3))
    val analyzer = new LogAnalyzer(requests)
  }

  "LogAnalyzer" should {

    "count all requests" in new Context()  {
      analyzer.countAll === 3
    }

    "sum size requests" in new Context()  {
      analyzer.requestsSum === 54359
    }

    "calc error rate" in new Context()  {
      analyzer.errorRate === (1.0 / 3)
    }

  }

}
