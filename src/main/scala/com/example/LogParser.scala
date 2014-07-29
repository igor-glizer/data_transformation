package com.example
import org.joda.time._
import com.netaporter.uri.Uri
import org.joda.time.format.DateTimeFormat

/**
 * Created by Igor_Glizer on 7/29/14.
 */
object LogParser {
  case class Request(host : String, idnet : String, authuser : String, date : DateTime,
                     request : String, status : Int, bytes : Int, url : Option[Uri], userAgent : String)
  def parse(lines : List[String]) : List[Request] = {
    val dateStringFormat = DateTimeFormat.forPattern("[dd/MMM/yyyy:HH:mm:ss +0000]")
    var i = 0
    List.fill(lines.length) {
        val elements = ElementExtractor.elements(lines(i), ' ')
        i = i + 1
        val host = elements(0)
        val idnet = elements(1)
        val authuser = elements(3)
        val date = dateStringFormat.parseDateTime(elements(4))
        val request = elements(5)
        val status = elements(6).toInt
        val bytes = elements(7).toInt
        val url = elements(8) match {
          case "_" => None
          case _ => Some(Uri.parse(elements(8)))
        }
        val userAgent = elements(9)

        Request(host, idnet, authuser, date, request, status, bytes, url, userAgent)
      }
    }
}