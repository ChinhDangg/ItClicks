package dev.chinh.itcanclick.ui.layout.center

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chinh.itcanclick.task.action.mouse.MouseClickInfo

class MouseClickSelectionState(
    initialName: String = "Mouse Click",
    initialClicks: Int = 1,
    initialDelay: Int = 0
) {
    var name by mutableStateOf(initialName)
    var numClicks by mutableStateOf(initialClicks)
    var delay by mutableStateOf(initialDelay)
}

@Composable
fun MouseClickEditor(
    onClick: (MouseClickInfo) -> Unit, // Callback to \"return\" the data
    modifier: Modifier = Modifier
) {
    val formState = remember { MouseClickSelectionState() }

    SelectionPanel(
        onClickSaved = {
            val newInfo = MouseClickInfo(
                numClicks = formState.numClicks,
                delay = formState.delay,
                name = formState.name
            )
            onClick(newInfo)
        }
    ) {
        MouseClickSelection(formState, modifier)
    }
}

@Composable
fun MouseClickSelection(
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
            state.numClicks.toString(),
            onValueChange = { state.numClicks = it.toIntOrNull() ?: 0 }
        ) {
            Spacer(modifier = Modifier.width(5.dp))
            TextButton(
                "+1",
                onClick = {
                    state.numClicks += 1
                }
            )
        }

        // Delay Input
        TextField(
            "Delay (milliseconds)",
            state.delay.toString(),
            onValueChange = { state.delay = it.toIntOrNull() ?: 0 }
        ) {
            Spacer(modifier = Modifier.width(5.dp))
            TextButton(
                "+50",
                onClick = {
                    state.delay += 50
                }
            )
        }
    }
}