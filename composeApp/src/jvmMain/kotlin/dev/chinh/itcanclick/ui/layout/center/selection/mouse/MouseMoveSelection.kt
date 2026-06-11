package dev.chinh.itcanclick.ui.layout.center.selection.mouse

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.chinh.itcanclick.task.action.mouse.MouseMoveInfo
import dev.chinh.itcanclick.ui.layout.center.selection.CaptureScreenModel
import dev.chinh.itcanclick.ui.layout.center.selection.LabeledCheckbox
import dev.chinh.itcanclick.ui.layout.center.selection.SelectionPanel
import dev.chinh.itcanclick.ui.layout.center.selection.TextButton
import dev.chinh.itcanclick.ui.layout.center.selection.TextField
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import java.awt.Rectangle

class MouseMoveSelectionState(
    initialIsExact: Boolean = false,
    initialName: String = "Mouse Move"
) {
    var isExact by mutableStateOf(initialIsExact)
    var name by mutableStateOf(initialName)
}

@Composable
fun MouseMoveEditor(
    onClick: (MouseMoveInfo) -> Unit,
    applicationContext: ApplicationContext,
    modifier: Modifier = Modifier
) {
    val formState = remember { MouseMoveSelectionState() }
    val captureModel = remember { applicationContext.getBean<CaptureScreenModel>() }

    SelectionPanel(
        onClickSaved = {
            val newInfo = MouseMoveInfo(
                Rectangle(captureModel.x, captureModel.y, captureModel.width, captureModel.height),
                formState.isExact,
                formState.name
            )
            onClick(newInfo)
        }
    ) {
        MouseMoveSelection(formState, captureModel, modifier)
    }
}

@Composable
fun MouseMoveSelection(
    state: MouseMoveSelectionState,
    captureModel: CaptureScreenModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Task Name Input
        TextField(
            "Task Name",
            value = state.name,
            onValueChange = {
                state.name = it
            },
        ) {}

        val onStartClick = {
            captureModel.startCapture()
        }
        val onStopClick = {
            captureModel.stopCapture()
        }

        LabeledCheckbox(
            "Exact",
            state.isExact,
            onCheckedChange = { state.isExact = it })

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
                        val value = it.toIntOrNull() ?: 1
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
                        val value = it.toIntOrNull() ?: 1
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