package dev.chinh.itcanclick.task

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun saveImageSafely(image: BufferedImage, filename: String) {
    val os = System.getProperty("os.name").lowercase()
    val userHome = System.getProperty("user.home")
    val appName = "ItCanClick"

    // Determine the safe, writable OS directory
    val baseDir = when {
        os.contains("win") -> File(System.getenv("APPDATA"), appName)
        os.contains("mac") -> File(userHome, "Library/Application Support/$appName")
        else -> File(userHome, ".local/share/$appName") // Linux standard
    }

    val imageFolder = File(baseDir, "TaskImages")

    if (!imageFolder.exists()) {
        imageFolder.mkdirs()
    }

    val outputFile = File(imageFolder, "$filename.png")
    ImageIO.write(image, "png", outputFile)

    println("Image saved safely to: ${outputFile.absolutePath}")
}

