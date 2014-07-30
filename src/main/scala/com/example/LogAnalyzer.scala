package com.example

/**
 * Created by Igor_Glizer on 7/29/14.
 */
class LogAnalyzer(log : List[Request]) {


  def countAll = log.size

  def requestsSum = log.map(_.bytes).sum

  def errorRate = log.count(r => isErrorStatus(r.status)).toDouble / countAll

  private def isErrorStatus(status : Int) : Boolean = status >= 400 && status < 600

}
