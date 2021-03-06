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
    PDFToImage.main(Array("-outputPrefix", outputPath, fileName))
  }

  def convertPageToPng(fileName: String, outputPath : String) = {
    PDFToImage.main(Array("-imageType", "png", "-outputPrefix",  outputPath, fileName))
  }

  def extractTextFromFile(fileName: String) = {
    ExtractText.main(Array("-html", fileName, fileName.stripSuffix(".pdf") + ".txt"))
  }

  def extractImagesFromFile(fileName: String) = {
    ExtractImages.main(Array(fileName))
  }
}
