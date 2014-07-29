package com.example
import org.joda.time.DateTime
import com.netaporter.uri.Uri
/**
 * Created by Igor_Glizer on 7/29/14.
 */
case class Request(host : String, idnet : String, authuser : String, date : DateTime,
                   request : String, status : Int, bytes : Int, url : Option[Uri], userAgent : String)