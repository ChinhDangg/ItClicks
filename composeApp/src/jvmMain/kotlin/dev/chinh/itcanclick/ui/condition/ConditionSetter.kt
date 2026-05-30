package dev.chinh.itcanclick.ui.condition

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.io.File

@Composable
fun ConditionViewer() {
    var currentImagePath by remember { mutableStateOf("") }



    val currentBitmap = remember(currentImagePath) {
        loadBitmapFromFile(File(currentImagePath))
    }

    if (currentBitmap != null) {
        MainImageViewer(currentBitmap)
    }
}

@Composable
fun MainImageViewer(imageBitmap: ImageBitmap) {
    Image(
        bitmap = imageBitmap,
        contentDescription = "Loaded from disk"
    )
}

fun loadBitmapFromFile(file: File): ImageBitmap? {
    return try {
        // Read bytes and decode using Skia
        val bytes = file.readBytes()
        Image.makeFromEncoded(bytes).toComposeImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}