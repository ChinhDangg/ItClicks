package dev.chinh.itcanclick.ui.layout.center.selection.mouse

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chinh.itcanclick.task.action.mouse.MouseBaseInfo
import dev.chinh.itcanclick.task.type.MouseType
import dev.chinh.itcanclick.ui.layout.center.selection.SelectionPanel
import dev.chinh.itcanclick.ui.layout.center.selection.TextButton
import dev.chinh.itcanclick.ui.layout.center.selection.TextField

class MousePressSelectionState(
    initialName: String = "Mouse Press",
    initialDelay: Int = 0
) {
    var name by mutableStateOf(initialName)
    var delay by mutableStateOf(initialDelay)
}

@Composable
fun MousePressEditor(
    onClick: (MouseBaseInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val formState = remember { MousePressSelectionState() }

    SelectionPanel(
        onClickSaved = {
            val newInfo = MouseBaseInfo(
                formState.delay,
                formState.name,
                MouseType.MOUSE_PRESS
            )
            onClick(newInfo)
        }
    ) {
        MousePressSelection(formState, modifier)
    }
}

@Composable
fun MousePressSelection(
    state: MousePressSelectionState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            "Task Name",
            state.name,
            onValueChange = { state.name = it },
        ) {}

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