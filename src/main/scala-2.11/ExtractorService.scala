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

    val outputPath = args.headOption match {
      case Some(x) => x
      case None => "/Users/jonathanjayet/Desktop/TestDecoupe/blabla"
    }

    val modifyCallbackDirectory: Callback = {
      case x if (x.toString.endsWith(".pdf")) =>
        println(s"------- Treating $x -------")
        println("Now splitting by page")
        Extractor.PdfExtractor.splitPdfByPage(x.toString, outputPath)
        println("Done splitting by page")
        println("Now converting to image")
        Extractor.PdfExtractor.convertPageToJpeg(x.toString)
        println("Done converting to image")
        println("Now extracting text")
        Extractor.PdfExtractor.extractTextFromFile(x.toString)
        println("Done extracting text")
        println("Now extracting images")
        Extractor.PdfExtractor.extractImagesFromFile(x.toString)
        println("Done extracting images")

        println(s"------- Done treating $x -------")
      case _ =>
    }

    val desktop = Paths get "/Users/jonathanjayet/Desktop"

    fileMonitorActor ! RegisterCallback(
      ENTRY_MODIFY,
      None,
      recursive = false,
      path = desktop,
      modifyCallbackDirectory)
  }
}
