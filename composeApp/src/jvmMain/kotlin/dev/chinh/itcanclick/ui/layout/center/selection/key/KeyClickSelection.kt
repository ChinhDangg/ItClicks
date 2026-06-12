package dev.chinh.itcanclick.ui.layout.center.selection.key

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chinh.itcanclick.task.action.key.KeyInfo
import dev.chinh.itcanclick.task.action.key.KeyListener
import dev.chinh.itcanclick.ui.layout.center.selection.SelectionPanel
import dev.chinh.itcanclick.ui.layout.center.selection.TextButton
import dev.chinh.itcanclick.ui.layout.center.selection.TextField

class KeyClickSelectionState(
    initialName: String = "Key Click",
    initialKeys: String = "",
    initialDelay: Int = 0,
) {
    var name by mutableStateOf(initialName)
    var keys by mutableStateOf(initialKeys)
    var delay by mutableStateOf(initialDelay)
}

@Composable
fun KeyClickEditor(
    onClick: (KeyInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val formState = remember { KeyClickSelectionState() }

    SelectionPanel(
        onClickSaved = {

        }
    ) {
        KeyClickSelection(formState, modifier)
    }
}

@Composable
fun KeyClickSelection(
    state: KeyClickSelectionState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            "Task Name",
            state.name,
            onValueChange = { state.name = it },
        ) {}

        TextField(
            "Keys",
            state.keys,
            isSingleLine = false,
            onValueChange = { state.keys = it },
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
