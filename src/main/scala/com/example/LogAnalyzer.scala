package com.example

/**
 * Created by Igor_Glizer on 7/29/14.
 */
object LogAnalyzer {

  type Log = List[Request]

  case class ErrorRateDetails(clientRate : Double, serverRate : Double)

  val countAll = (log : Log) =>  log.size

  val requestsSum = (log : Log) => sumLog(log)(l => l.bytes)

  val totalErrorRate = (log : Log) => countStatuses(log)(isErrorStatus).toDouble / countAll(log)

  val errorRateDetails = (log : Log) => {
    val errorRate = totalErrorRate(log)
    if (errorRate == 0)
      ErrorRateDetails(0, 0)
    else
      ErrorRateDetails(errorRate / countStatuses(log)(isClientErrorStatus).toDouble, errorRate / countStatuses(log)(isServerErrorStatus).toDouble)
  }


  private val countStatuses = (log : Log) => (statusChecker : Int => Boolean) =>  log.count(r => statusChecker(r.status))

  private val sumLog = (log : Log) => (takeInt : Request => Int) =>  log.map(takeInt).sum

  private val isErrorStatus = (status : Int) => isClientErrorStatus(status) || isServerErrorStatus(status)

  private val isClientErrorStatus = (status : Int) => status >= 400 && status < 500
  private val isServerErrorStatus = (status : Int) => status >= 500 && status < 600

}
