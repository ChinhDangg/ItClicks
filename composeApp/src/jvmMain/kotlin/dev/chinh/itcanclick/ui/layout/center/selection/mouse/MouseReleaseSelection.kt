package dev.chinh.itcanclick.ui.layout.center.selection.mouse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chinh.itcanclick.task.action.mouse.MouseBaseInfo
import dev.chinh.itcanclick.task.type.MouseType
import dev.chinh.itcanclick.ui.layout.center.selection.SelectionPanel
import dev.chinh.itcanclick.ui.layout.center.selection.TextButton
import dev.chinh.itcanclick.ui.layout.center.selection.TextField

class MouseReleaseSelectionState(
    initialDelay: Int = 0,
    initialName: String = "Mouse Release"
) {
    var delay by mutableStateOf(initialDelay)
    var name by mutableStateOf(initialName)
}

@Composable
fun MouseReleaseEditor(
    onClick: (MouseBaseInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    val formState = remember { MouseReleaseSelectionState() }

    SelectionPanel(
        onClickSaved = {
            val newInfo = MouseBaseInfo(
                formState.delay,
                formState.name,
                MouseType.MOUSE_RELEASE
            )
            onClick(newInfo)
        }
    ) {
        MouseReleaseSelection(formState, modifier)
    }
}

@Composable
fun MouseReleaseSelection(
    state: MouseReleaseSelectionState,
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

