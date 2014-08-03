package com.example

import org.joda.time._
import com.netaporter.uri.Uri
import org.joda.time.format.DateTimeFormat

import scala.util.{Try, Success, Failure}

/**
 * Created by Igor_Glizer on 7/29/14.
 */
object LogParser {

  def parse(lines: List[String]): List[Request] = {
    lines collect {
      case ExtractedLine(host :: idnet :: authuser :: ParsedDate(date) :: _ :: ParsedUrl(path) :: status :: bytes :: referrer :: userAgent) =>
        Request(host, idnet, authuser, date, path, status.toInt, bytes.toInt, stripElement(userAgent.head))
    }

  }

  object ParsedDate {
    def unapply(dateString: String): Option[DateTime] =
      Try(DateTimeFormat.forPattern("[dd/MMM/yyyy:HH:mm:ss").parseDateTime(dateString)) match {
        case Success(date) => Some(date)
        case Failure(_) => None
      }
  }

  object ParsedUrl {
    def unapply(requestString: String) = {
      val _ :: pathString :: _ = ElementExtractor.elements(stripElement(requestString), ' ')
      Try(Uri.parse(pathString)) match {
        case Success(path) => Some(path)
        case Failure(_) => None
      }
    }
  }

  object ExtractedLine {
    def unapply(line: String) = Some(ElementExtractor.elements(line, ' '))
  }

  private def stripElement(element: String) = {
    element.tail.init
  }
}