package dev.chinh.itcanclick.ui.layout.center

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chinh.itcanclick.task.action.mouse.MouseClickInfo

class MouseClickSelectionState(
    initialName: String = "Mouse Click",
    initialClicks: String = "1",
    initialDelay: String = "0"
) {
    var name by mutableStateOf(initialName)
    var numClicks by mutableStateOf(initialClicks)
    var delay by mutableStateOf(initialDelay)
}

@Composable
fun MouseClickSelection(
    onClick: (MouseClickInfo) -> Unit, // Callback to \"return\" the data
    modifier: Modifier = Modifier
) {
    val formState = remember { MouseClickSelectionState() }

    SelectionPanel(
        onClickSaved = {
            val newInfo = MouseClickInfo(
                numClicks = formState.numClicks.toInt(),
                delay = formState.delay.toInt(),
                name = formState.name
            )
            onClick(newInfo)
        }
    ) {
        MouseSelection(formState, modifier)
    }
}

@Composable
fun MouseSelection(
    state: MouseClickSelectionState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Task Name Input
        TextField(
            "Task Name",
            state.name,
            onValueChange = { state.name = it },
        ) {}

        // Number of Clicks Input
        TextField(
            "Number of Clicks",
            state.numClicks,
            onValueChange = { state.numClicks = it }
        ) {
            Spacer(modifier = Modifier.width(5.dp))
            TextButton(
                "+1",
                onClick = {
                    val current = state.numClicks.toIntOrNull() ?: 0
                    state.numClicks = (current + 1).toString()
                }
            )
        }

        // Delay Input
        TextField(
            "Delay (milliseconds)",
            state.delay,
            onValueChange = { state.delay = it }
        ) {
            Spacer(modifier = Modifier.width(5.dp))
            TextButton(
                "+50",
                onClick = {
                    val current = state.delay.toIntOrNull() ?: 0
                    state.delay = (current + 50).toString()
                }
            )
        }
    }
}