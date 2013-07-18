package controllers

import play.api._
import play.api.mvc._
import java.io.File

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Pennis"))
  }

  def uploadFile = Action(parse.multipartFormData) {
    request => {
      request.body.file("fileUpload").map {
        image =>
          val imageFileName = image.filename
          val contentType = image.contentType.get
          image.ref.moveTo(new File("/home/images/" + imageFileName))
          Ok("FileUploaded")
      }.getOrElse{
        BadRequest("No upload bitch")
      }
  }
}
}