package controllers

import play.api._
import play.api.mvc._
import java.io._
import javax.imageio.{ImageReader, ImageIO}
import play.api.libs.Files
import java.net.URLConnection
import scala.Some
import com.mongodb.casbah.{MongoClient, MongoConnection}
import com.mongodb.casbah.gridfs.GridFS
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import scala.Some


object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Pennis"))
  }


  def uploadFile = Action(parse.anyContent) {
    request => {
        request.body.asRaw match{
          case Some(bytes) => {
            val srcimagefile = bytes.asFile

            val is = new BufferedInputStream(new FileInputStream(srcimagefile))
            val mimeType = URLConnection.guessContentTypeFromStream(is)

            var extension : String = ""
            mimeType match{
              case "image/jpeg" => extension = "jpg"
              case "image/png"  => extension = "png"
            }

            val mongo = MongoConnection()("test")

            val gridFs = GridFS(mongo)

            val fileInputStream = new FileInputStream(srcimagefile)

            var gsFile = gridFs.createFile(fileInputStream)
            gsFile.filename = "Pennis." + extension
            gsFile.save()

            val dbObj = gsFile.asDBObject

            val id = dbObj.get("_id")

            val mongoObject = MongoDBObject.newBuilder

            mongoObject += "imageId" -> id

            val client = MongoClient()("test")("testGridFs").insert(mongoObject.result())

          Ok("Nice")
        }
      }
  }


}

  def loadFile(id : String) = Action {
    val mongo = MongoConnection()("test")
    val gridfs = GridFS(mongo)

    var gsFile = gridfs.find(new ObjectId(id))

    val is = gsFile.inputStream

    val bytes = fileToBytes(is)

    Ok(bytes)

  }

  def fileToBytes(inStream: InputStream) : Array[Byte] = {
    val outStream = new ByteArrayOutputStream
    try {
      var reading = true
      while ( reading ) {
        inStream.read() match {
          case -1 => reading = false
          case c => outStream.write(c)
        }
      }
      outStream.flush()
    }
    finally
    {
      inStream.close()
    }

    return outStream.toByteArray
  }
}