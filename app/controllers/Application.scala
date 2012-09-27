package controllers

import scala.xml._
import play.api._
import play.api.mvc._
import play.libs.WS
import play.libs.F.Function
import scala.io.Source
import scala.xml.XML
import scala.xml.parsing.{ ConstructingParser, XhtmlParser }
import services.GoogleStreetView
import services.YahooLocalSearch
import services.XML2JSON
import scala.collection.mutable.HashMap

object Application extends Controller {

  /**
   * もげえええ
   */
  def index = Action {
    val homePage = WS.url("http://ally.to/version/ios.xml").get()
    println(homePage.get().getBody())
    Ok(views.html.index(homePage.get().getBody()))
  }

  /**
   * あひゅおおお
   */
  def nyoro = Action {
    Ok(views.html.index("ahhyooooooo"))
  }

  /**
   *
   */
  def streetview = Action { request =>
    println(request.queryString.getOrElse("q", ""))
    println(request.queryString.getOrElse("id", ""))

    //    GoogleStreetView.getWithLatLon(param)
    Ok(views.html.index("ahhyooooooo"))
  }

  def yls = Action { request =>
    val map = Map("query" -> request.queryString.getOrElse("q", Seq[String](""))(0),
      "lat" -> request.queryString.getOrElse("lat", Seq[String](""))(0),
      "lon" -> request.queryString.getOrElse("lon", Seq[String](""))(0))

    var res = YahooLocalSearch.get(map): String
    var xml = scala.xml.XML.loadString(res)
//    println(res)
    var jsonresp = XML2JSON(xml)
    Ok(views.txt.yls(jsonresp)).as(JSON)
  }
}

case class RequestParam(lat: Double, lon: Double, id: Int, query: String, q: String)
