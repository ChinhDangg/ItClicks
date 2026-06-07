package dev.chinh.itcanclick.ui.layout.center

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chinh.itcanclick.task.action.mouse.MouseMoveInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage

class MouseMoveSelectionState(
    initialRect: Rectangle? = null,
    initialIsExact: Boolean = false,
    initialName: String = "Mouse Move"
) {
    var rect by mutableStateOf(initialRect)
    var isExact by mutableStateOf(initialIsExact)
    var name by mutableStateOf(initialName)
}

@Composable
fun MouseMoveEditor(
    onClick: (MouseMoveInfo) -> Unit,
    applicationContext: ApplicationContext,
    modifier: Modifier = Modifier
) {
    val formState = remember { MouseMoveSelectionState()}

    SelectionPanel(
        onClickSaved = {

        }
    ) {
        MouseMoveSelection(formState, applicationContext, modifier)
    }
}

@Composable
fun MouseMoveSelection(
    state: MouseMoveSelectionState,
    applicationContext: ApplicationContext,
    modifier: Modifier = Modifier
) {

    var isExact by remember { mutableStateOf(state.isExact) }
    val captureModel = remember { applicationContext.getBean<CaptureScreenModel>() }
    var width by remember { mutableStateOf(50) }
    var height by remember { mutableStateOf(50) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Task Name Input
        TextField(
            "Task Name",
            state.name,
            onValueChange = { state.name = it },
        ) {}

        val onStartClick = {
            captureModel.startCapture(width, height)
        }
        val onStopClick = {
            captureModel.stopCapture()
        }

        LabeledCheckbox("Exact", isExact, onCheckedChange = { isExact = it })

        ImageViewerScreen(
            image = captureModel.image,
            x = captureModel.x,
            y = captureModel.y,
            width = width,
            height = height,
            onStartClick = onStartClick,
            onStopClick = onStopClick
        )
    }
}


@Composable
fun ImageViewerScreen(
    image: ImageBitmap?,
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    onStartClick: () -> Unit,
    onStopClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // Image / Screen Viewer
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            shape = RoundedCornerShape(20.dp)
        ) {
            if (image != null) {
                Image(
                    bitmap = image,
                    contentDescription = "Captured screen",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "No Image",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Press Start to begin capture",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Coordinate Info
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Column {
                CoordinateItem("X:", x.toString())
                CoordinateItem("Y:", y.toString())
            }

            Column {
                CoordinateItem("W:", width.toString())
                CoordinateItem("H:", height.toString())
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextButton("Start", onClick = onStartClick)
            TextButton("Stop", onClick = onStopClick)
        }
    }
}

@Composable
private fun CoordinateItem(
    label: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = value,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )
    }
}