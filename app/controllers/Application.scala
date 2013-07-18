package controllers

import play.api._
import play.api.mvc._
import java.io._
import javax.imageio.{ImageReader, ImageIO}
import play.api.libs.Files
import java.net.URLConnection
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

            val destimageFile = new File("/home/images/pennis." + extension)
            is.close()

            Files.moveFile(srcimagefile , destimageFile , true)

            val pennis = "Pennis!"



          Ok("Nice")
        }
      }
      /*
      request.body.file("fileUpload").map {
        image =>
          val imageFileName = image.filename
          val contentType = image.contentType.get
          image.ref.moveTo(new File("/home/images/" + imageFileName))
          Ok("FileUploaded")
      }.getOrElse{
        BadRequest("No upload bitch")
      }
      */
  }
}
}