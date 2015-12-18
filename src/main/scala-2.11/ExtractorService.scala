import akka.actor.ActorSystem
import com.beachape.filemanagement.MonitorActor
import com.beachape.filemanagement.RegistryTypes._
import com.beachape.filemanagement.Messages._

import java.io.{FileWriter, BufferedWriter}

import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds._

/**
 * Created by jonathanjayet on 01/07/15.
 */
object ExtractorService {
  def main(args: Array[String]) = {
    implicit val system = ActorSystem("actorSystem")
    val fileMonitorActor = system.actorOf(MonitorActor())
    val home_path = System.getProperty("user.home")
    val watch_dir = Paths get (home_path + "/Desktop/PDFs")
//    val outputPath = args.headOption match {
//      case Some(x) => x
//      case None => home_path + "/Desktop/PDFs"
//    }

    val modifyCallbackDirectory: Callback = {
      case x if (x.toString.endsWith(".pdf")) =>
        println(s"------- Treating $x -------")

        val pdf_dir = x.toString.dropRight(4) + "/pdf/"
        val jpg_dir = x.toString.dropRight(4) + "/jpg/"
        val png_dir = x.toString.dropRight(4) + "/png/"
        new java.io.File(pdf_dir).mkdirs
        new java.io.File(jpg_dir).mkdirs
        new java.io.File(png_dir).mkdirs

        println("Now splitting by page")
        Extractor.PdfExtractor.splitPdfByPage(x.toString, pdf_dir)
        println("Done splitting by page")
        println("Now converting to image")
        Extractor.PdfExtractor.convertPageToJpeg(x.toString, jpg_dir)
        Extractor.PdfExtractor.convertPageToPng(x.toString, png_dir)
        println("Done converting to image")
//        println("Now extracting text")
//        Extractor.PdfExtractor.extractTextFromFile(x.toString)
//        println("Done extracting text")
//        println("Now extracting images")
//        Extractor.PdfExtractor.extractImagesFromFile(x.toString)
//        println("Done extracting images")

        println(s"------- Done treating $x -------")
      case _ =>
    }

    fileMonitorActor ! RegisterCallback(
      ENTRY_MODIFY,
      None,
      recursive = false,
      path = watch_dir,
      modifyCallbackDirectory)
  }
}
