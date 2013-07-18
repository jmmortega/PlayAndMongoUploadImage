import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "uploadFile"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.mongodb" %% "casbah" % "2.6.2",
    jdbc,
    anorm
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
