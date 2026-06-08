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
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    initialX: Int = 0,
    initialY: Int = 0,
    initialWidth: Int = 50,
    initialHeight: Int = 50,
    initialIsExact: Boolean = false,
    initialName: String = "Mouse Move"
) {
    var x by mutableStateOf(initialX)
    var y by mutableStateOf(initialY)
    var width by mutableStateOf(initialWidth)
    var height by mutableStateOf(initialHeight)
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
            captureModel.startCapture()
        }
        val onStopClick = {
            captureModel.stopCapture()
        }

        LabeledCheckbox("Exact", isExact, onCheckedChange = { isExact = it })

        ImageViewerScreen(
            captureModel = captureModel,
            onStartClick = onStartClick,
            onStopClick = onStopClick
        )
    }
}


@Composable
fun ImageViewerScreen(
    captureModel: CaptureScreenModel,
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
            if (captureModel.image != null) {
                Image(
                    bitmap = captureModel.image!!,
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                TextField(
                    textContent = "X:",
                    value = captureModel.x.toString(),
                    onValueChange = {
                        captureModel.x = it.toIntOrNull() ?: 0
                    }
                ) {}
                TextField(
                    textContent = "Y:",
                    value = captureModel.y.toString(),
                    onValueChange = {
                        captureModel.y = it.toIntOrNull() ?: 0
                    }
                ) {}
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(4f),
            ) {
                TextField(
                    textContent = "W:",
                    value = captureModel.width.toString(),
                    onValueChange = {
                        val value = it.toIntOrNull() ?: 0
                        captureModel.width = value
                    }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        TextButton("+10", onClick = {
                            captureModel.width += 10
                        })
                        TextButton("-1", onClick = {
                            captureModel.width -= 1
                        })
                    }
                }

                TextField(
                    textContent = "H:",
                    value = captureModel.height.toString(),
                    onValueChange = {
                        val value = it.toIntOrNull() ?: 0
                        captureModel.height = value
                    }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        TextButton("+10", onClick = {
                            captureModel.height += 10
                        })
                        TextButton("-1", onClick = {
                            captureModel.height -= 1
                        })
                    }
                }
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
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.width(6.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier.width(70.dp)
        )
    }
}