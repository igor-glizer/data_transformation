package com.example

import org.specs2.mutable.{SpecificationWithJUnit}
import com.netaporter.uri.Uri

/**
 * Created by Igor_Glizer on 7/29/14.
 */
class LogParserTest extends SpecificationWithJUnit {


  "LogParser" should {

    "parse proper line line" in {
      val line = """166.147.120.17 -  -  [18/Jul/2014:00:00:01 +0000] "GET / HTTP/1.1" 200 41371 "http://lm.facebook.com/l.php?u=http%3A%2F%2Fwww.hwhn.org%2F&h=BAQHjyUsT&enc=AZOT_bGPzQxJmJiWm8_k6KPsRRq6mx1oca5ceGzzgGKEHXOTzua34sO38MSzR2ktntcPeLeuG6dnfALyMEDyRhR_a9J46c7PXb-HTWCBe9dSQw9DKrHGcOzUWDy8GKkeqLVdKmnjbHJcTpYGTF7iMFx2&s=1" "Mozilla/5.0 (Linux; U; Android 4.3; en-us; SAMSUNG-SGH-I747 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30""""
      val request = LogParser.parse(List(line))(0)
      request.host ===  "166.147.120.17"
      request.idnet === "-"
      request.authuser === "-"
      request.date.toString("dd/MMM/yyyy:HH:mm:ss")  === "18/Jul/2014:00:00:01"
      request.path === Uri.parse("/")
      request.status === 200
      request.bytes === 41371
      request.userAgent ==== "Mozilla/5.0 (Linux; U; Android 4.3; en-us; SAMSUNG-SGH-I747 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"
    }

    "drop bad  line" in {
      val line = """166.147.120.17 -  -  [18/Jul/2014:00:00:01 +0000] "GET ## HTTP/1.1" 200 41371 "http://lm.facebook.com/l.php?u=http%3A%2F%2Fwww.hwhn.org%2F&h=BAQHjyUsT&enc=AZOT_bGPzQxJmJiWm8_k6KPsRRq6mx1oca5ceGzzgGKEHXOTzua34sO38MSzR2ktntcPeLeuG6dnfALyMEDyRhR_a9J46c7PXb-HTWCBe9dSQw9DKrHGcOzUWDy8GKkeqLVdKmnjbHJcTpYGTF7iMFx2&s=1" "Mozilla/5.0 (Linux; U; Android 4.3; en-us; SAMSUNG-SGH-I747 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30""""
      val requests = LogParser.parse(List(line))
      requests.isEmpty === true
    }

  }
}
