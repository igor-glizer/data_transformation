package com.example

import com.netaporter.uri.Uri
import org.joda.time.format.DateTimeFormat
/**
 * Created by Igor_Glizer on 7/29/14.
 */
object LogAnalyzer {

  type Log = Seq[Request]

  case class ErrorRateDetails(clientRate : Double, serverRate : Double)

  case class LogStats(requestsCount : Int, responseSum : Int, errorRate : Double)

  val countAll = (log : Log) =>  log.size

  val responseSum = (log : Log) => sumLog(log)(l => l.bytes)

  val totalErrorRate = (log : Log) => countStatuses(log)(isErrorStatus).toDouble / countAll(log)

  val errorRateDetails = (log : Log) => {
    val errorRate = totalErrorRate(log)
    if (errorRate == 0)
      ErrorRateDetails(0, 0)
    else
      ErrorRateDetails(errorRate / countStatuses(log)(isClientErrorStatus).toDouble, errorRate / countStatuses(log)(isServerErrorStatus).toDouble)
  }
  
  val topUrlStats = (log : Log, count : Int) => { statsByTop(log, 1)(_.path) }

  val topIpStats =  (log : Log, count : Int) => { statsByTop(log, 1)(_.host) }

  val erroneousPathErrors = (log : Log) => { filterStatuses(log)(isErrorStatus).groupBy(_.status).mapValues(countAll) }

  val hourlyStats = (log : Log) => { log.groupBy(_.date.hourOfDay().get()).mapValues(createStats) }

  private val countStatuses = (log : Log) => (statusFilter : Int => Boolean) =>  filterStatuses(log)(statusFilter).size

  private val filterStatuses = (log : Log) => (statusFilter : Int => Boolean) =>  log.filter(r => statusFilter(r.status))

  private val sumLog = (log : Log) => (takeInt : Request => Int) =>  log.map(takeInt).sum

  private val isErrorStatus = (status : Int) => isClientErrorStatus(status) || isServerErrorStatus(status)
  private val isClientErrorStatus = (status : Int) => isInRange(400, 500)(status)
  private val isServerErrorStatus = (status : Int) => isInRange(500, 600)(status)
  private val isInRange = (min : Int, max : Int) => (status : Int) => status >= min && status < max

  private def statsByTop[T] = (log : Log, count : Int) => (by : Request => T) => { top(log, 1)(by).mapValues(createStats) }
  private def top[T](log : Log, count : Int)(by : Request => T) = log.groupBy(by).toList.sortBy{ case (_, list) => list.size }.reverse.take(count).toMap

  private val createStats = (log : Log) => LogStats(countAll(log), responseSum(log), totalErrorRate(log))

}
