package services
import play.api._
import play.api.mvc._
import play.libs.WS
import play.libs.F.Function
import scala.io.Source
import scala.xml.XML
import scala.xml.parsing.{ ConstructingParser, XhtmlParser }
import scala.collection.mutable.HashMap
import scala.util.Random

object YahooLocalSearch {

  val API_URL = "http://search.olp.yahooapis.jp/OpenLocalPlatform/V1/localSearch?"
  val API_KEY = "mwuIWKOxg66hhfaioIcoO7mId_YJFiwIcFEWOcrCV9Jy1cD.4Mj2T6QJMWip7MeFI_mJ"

  def get(args: Map[String, String]): String = {
    println("reached yls get method")
    println(args.apply("query"))

    var cid = ""
    val random = new Random
    var seed = new String
    seed += ((random.nextInt(10)).toString)

    // ランダムにカセットを切り替える
    // 4136755b3cd84823420aec02ac373152 : 町ログ
    // cd246abc0da32d9d0e4990a54e3bf0e4 : 全国寺社ガイド
    // bf1929f6ab23005e9b80adf4fd0fc5ab : なんかママ向けのカフェとかそういうやつ
    // 132c700e0ebf5a1d386e9b725d7d1e6b : yahoo ロコ
    if (Integer.parseInt(seed) % 4 == 0) {
      cid = "4136755b3cd84823420aec02ac373152"
    } else if (Integer.parseInt(seed) % 4 == 1) {
      cid = "cd246abc0da32d9d0e4990a54e3bf0e4"
    } else if (Integer.parseInt(seed) % 4 == 2) {
      cid = "bf1929f6ab23005e9b80adf4fd0fc5ab"
    } else {
      cid = "4136755b3cd84823420aec02ac373152"
    }
    
    // 開始位置をすこしだけゆらがせる
    val random_2 = new Random
    var seed_2 = new String
    seed_2 += ((random_2.nextInt(10)).toString)
    var offset = (Integer.parseInt(seed_2)) % 3

    val res = WS.url(API_URL)
      .setQueryParameter("appid", API_KEY)
      .setQueryParameter("lat", args.getOrElse("lat", null))
      .setQueryParameter("lon", args.getOrElse("lon", null))
      .setQueryParameter("dist", args.getOrElse("0.5", null))
      .setQueryParameter("query", args.getOrElse("query", null))
      .setQueryParameter("sort", "dist")
      .setQueryParameter("start", offset.toString())
      .setQueryParameter("cid", cid)
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
