package controllers

import play.api._
import play.api.mvc._
import play.libs.WS
import play.libs.F.Function
import scala.io.Source
import scala.xml.XML
import scala.xml.parsing.{ ConstructingParser, XhtmlParser }
import services.GoogleStreetView
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
  def streetview = Action {request =>

    println(request.queryString.getOrElse("id", ""))
//    GoogleStreetView.getWithLatLon(param)
    Ok(views.html.index("ahhyooooooo"))
  }
}

case class StreetViewParam(lat:Double,lon:Double,id:Int)

