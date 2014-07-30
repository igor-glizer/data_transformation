package com.example
import org.joda.time._
import com.netaporter.uri.Uri
import org.joda.time.format.DateTimeFormat

/**
 * Created by Igor_Glizer on 7/29/14.
 */
object LogParser {

  def parse(lines : List[String]) : List[Request] = {
    val dateStringFormat = DateTimeFormat.forPattern("[dd/MMM/yyyy:HH:mm:ss")
    var i = 0
    List.fill(lines.length) {
        val elements = ElementExtractor.elements(lines(i), ' ')
        i = i + 1
        val host = elements(0)
        val idnet = elements(1)
        val authuser = elements(2)
        val date = dateStringFormat.parseDateTime(elements(3))
        val request = stripElement(elements(5))
        val status = elements(6).toInt
        val bytes = elements(7).toInt
        val url = elements(8) match {
          case """""_"""" => None
          case _ => Some(stripElement(elements(8)))
        }
        val userAgent = stripElement(elements(9))

        Request(host, idnet, authuser, date, request, status, bytes, url, userAgent)
      }
    }

  private def stripElement(element : String) = {
    element.tail.init
  }
}