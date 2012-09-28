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
    Ok(views.html.index())
  }

  /**
   * あひゅおおお
   */
  def nyoro = Action {
    Ok(views.html.index())
  }

  /**
   *
   */
  def streetview = Action { request =>
    println(request.queryString.getOrElse("q", ""))
    println(request.queryString.getOrElse("id", ""))

    //    GoogleStreetView.getWithLatLon(param)
    Ok(views.html.index())
  }

  def yls = Action { implicit request =>
    val params: collection.mutable.Map[String, Seq[String]] = collection.mutable.Map()
    params ++= request.body.asFormUrlEncoded.getOrElse[Map[String, Seq[String]]] { Map.empty }
    params ++= request.queryString
    val map = Map("query" -> params.getOrElse("q", Seq[String](""))(0),
      "lat" -> params.getOrElse("lat", Seq[String](""))(0),
      "lon" -> params.getOrElse("lon", Seq[String](""))(0))

    println(params.get("q"))
    println(params.getOrElse("lat", Seq[String](""))(0))
    println(params.getOrElse("lon", Seq[String](""))(0))

    var res = YahooLocalSearch.get(map): String
    var xml = scala.xml.XML.loadString(res)
    var jsonresp = XML2JSON(xml)

    println(res);
    
    Ok(views.txt.yls(jsonresp)).as(JSON).withHeaders("Access-Control-Allow-Origin" -> "*",
      "Access-Control-Allow-Methods" -> "POST, GET,PUT, DELETE, OPT",
      "Access-Control-Allow-Credentials" -> "true",
      "Access-Control-Allow-Headers" -> "X-PINGOTHER",
      "Access-Control-Max-Age" -> "86400")
  }
}

case class RequestParam(lat: Double, lon: Double, id: Int, query: String, q: String)
