package dev.chinh.itcanclick.ui.layout.center

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.awt.Point
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage

@Service
class ScreenCaptureService(
    private val robot: Robot
) {

    /**
     * Captures the entire primary screen.
     */
    fun captureFullScreen(): BufferedImage {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        return robot.createScreenCapture(Rectangle(screenSize))
    }

    /**
     * Captures a specific rectangular region of the screen.
     */
    fun captureRegion(x: Int, y: Int, width: Int, height: Int): BufferedImage {
        // Safety check to prevent Java AWT crashes if width/height are invalid
        val safeWidth = if (width > 0) width else 1
        val safeHeight = if (height > 0) height else 1

        val region = Rectangle(x, y, safeWidth, safeHeight)
        return robot.createScreenCapture(region)
    }

    fun getCurrentMouseLoc(): Point {
        return java.awt.MouseInfo.getPointerInfo().location
    }
}

@Component
class CaptureScreenModel(
    private val captureService: ScreenCaptureService
): ViewModel() {
    private var captureJob: Job? = null
    private var _image = mutableStateOf<ImageBitmap?>(null)
    private var _x = mutableStateOf(0)
    private var _y = mutableStateOf(0)

    val image: ImageBitmap?
        get() = _image.value
    val x: Int
        get() = _x.value
    val y: Int
        get() = _y.value

    fun startCapture(width: Int, height: Int) {
        if (captureJob?.isActive == true) return

        captureJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val mouseLoc = captureService.getCurrentMouseLoc()
                val bufferedImage = captureService.captureRegion(mouseLoc.x, mouseLoc.y, width, height)
                _image.value = bufferedImage.toComposeImageBitmap()
                _x.value = mouseLoc.x
                _y.value = mouseLoc.y
                delay(100)
            }
        }
    }

    fun stopCapture() {
        captureJob?.cancel()
        captureJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopCapture()
    }
}