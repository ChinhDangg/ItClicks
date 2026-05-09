package dev.chinh.itcanclick.task.condition

import org.bytedeco.javacpp.DoublePointer
import org.bytedeco.javacpp.Loader
import org.bytedeco.opencv.global.opencv_core
import org.bytedeco.opencv.global.opencv_imgproc
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_core.Point
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte


interface PixelCondition : Condition {

    data class MatchResult(val matchScore : Double, val rect : Rectangle)

    fun templateMatching(sourceImage : BufferedImage, targetImage : BufferedImage,
                                 rect : Rectangle, useCenter : Boolean) : MatchResult {
        // Load OpenCV native libraries
        Loader.load(opencv_core::class.java)

        // Convert BufferedImage to Mat
        val sourceMat: Mat = bufferedImageToMat(sourceImage)
        val targetMat: Mat = bufferedImageToMat(targetImage)

        // Perform template matching
        val result = Mat()
        opencv_imgproc.matchTemplate(targetMat, sourceMat, result, opencv_imgproc.TM_CCOEFF_NORMED)

        // Find best match score
        val minVal = DoublePointer(1)
        val maxVal = DoublePointer(1)
        val minLoc = Point()
        val maxLoc = Point()

        opencv_core.minMaxLoc(result, minVal, maxVal, minLoc, maxLoc, null)

        val maxLocXInLocal =
            if (!useCenter) maxLoc.x() else (rect.x - (targetImage.width - sourceImage.width) / 2 + maxLoc.x())
        val maxLocYInLocal =
            if (!useCenter) maxLoc.y() else (rect.y - (targetImage.height - sourceImage.height) / 2 + maxLoc.y())

        return MatchResult(maxVal.get(), Rectangle(maxLocXInLocal, maxLocYInLocal, sourceImage.width, sourceImage.height))
    }

    private fun bufferedImageToMat(image : BufferedImage) : Mat {
        var image = image

        // Check if the BufferedImage is already in a compatible format
        // javacv probably use 3byte-bgr format for buffered image
        if (image.type != BufferedImage.TYPE_3BYTE_BGR) {
            val convertedImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_3BYTE_BGR)
            val g = convertedImage.createGraphics()
            g.drawImage(image, 0, 0, null)
            g.dispose()
            image = convertedImage // Replace with converted image
        }

        // Extract pixel data as byte array
        val pixels = (image.raster.getDataBuffer() as DataBufferByte).getData()

        // Create OpenCV Mat with correct format
        val mat = Mat(image.height, image.width, opencv_core.CV_8UC3)
        mat.data().put(*pixels)

        return mat
    }

}