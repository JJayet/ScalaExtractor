package Extractor

import org.apache.pdfbox.tools.{ExtractImages, PDFToImage, ExtractText, PDFSplit}

/**
 * Created by jonathanjayet on 01/07/15.
 */
object PdfExtractor {
  def splitPdfByPage(fileName: String, outputPath : String) = {
    PDFSplit.main(Array(fileName, "-outputPrefix", outputPath))
  }

  def convertPageToJpeg(fileName: String, outputPath : String) = {
    PDFToImage.main(Array(fileName, "-outputPrefix", outputPath))
  }

  def extractTextFromFile(fileName: String) = {
    ExtractText.main(Array("-html", fileName, fileName.stripSuffix(".pdf") + ".txt"))
  }

  def extractImagesFromFile(fileName: String) = {
    ExtractImages.main(Array(fileName))
  }
}
