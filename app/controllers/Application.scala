package controllers

import play.api._
import play.api.mvc._
import java.io.File

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Pennis"))
  }


  def uploadFile = Action(parse.anyContent) {
    request => {

      request.body.asRaw match{
        case Some(bytes) => {
          val filebites = bytes.asFile


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