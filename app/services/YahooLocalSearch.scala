package services
import play.api._
import play.api.mvc._
import play.libs.WS
import play.libs.F.Function
import scala.io.Source
import scala.xml.XML
import scala.xml.parsing.{ ConstructingParser, XhtmlParser }
import scala.collection.mutable.HashMap

object YahooLocalSearch {

  val API_URL = "http://search.olp.yahooapis.jp/OpenLocalPlatform/V1/localSearch?"
  val API_KEY = "mwuIWKOxg66hhfaioIcoO7mId_YJFiwIcFEWOcrCV9Jy1cD.4Mj2T6QJMWip7MeFI_mJ"

  def get(args: Map[String, String]):String = {
    println("reached yls get method")
    println(args.apply("query"))

    val res = WS.url(API_URL)
      .setQueryParameter("appid", API_KEY)
      .setQueryParameter("lat", args.getOrElse("lat", null))
      .setQueryParameter("lon", args.getOrElse("lon", null))
      .setQueryParameter("dist", args.getOrElse("0.5", null))
      .setQueryParameter("query", args.getOrElse("query", null))
      .get()
    //    val body = res.get().getBody()

//    println(res.get().getBody())
    res.get().getBody()
  }

  def main(args: Array[String]) {
    var map = Map("query" -> "京都")
    get(map)
  }
}
