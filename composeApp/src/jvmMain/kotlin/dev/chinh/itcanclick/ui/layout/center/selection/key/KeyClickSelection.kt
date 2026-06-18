package dev.chinh.itcanclick.ui.layout.center.selection.key

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chinh.itcanclick.task.action.key.KeyInfo
import dev.chinh.itcanclick.task.action.key.KeyListener
import dev.chinh.itcanclick.task.action.key.MacroEditorState
import dev.chinh.itcanclick.task.action.key.MacroToken
import dev.chinh.itcanclick.task.action.key.TextBlockUiState
import dev.chinh.itcanclick.ui.layout.center.selection.SelectionPanel
import dev.chinh.itcanclick.ui.layout.center.selection.TextButton
import dev.chinh.itcanclick.ui.layout.center.selection.TextField
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext

class KeyClickSelectionState(
    initialName: String = "Key Click",
    initialKeys: List<Int> = emptyList(),
    initialDelay: Int = 0,
) {
    var name by mutableStateOf(initialName)
    var keys by mutableStateOf(initialKeys)
    var delay by mutableStateOf(initialDelay)
}

@Composable
fun KeyClickEditor(
    onClick: (KeyInfo) -> Unit,
    applicationContext: ApplicationContext,
    modifier: Modifier = Modifier
) {
    val formState = remember { KeyClickSelectionState() }
    val macroEditorState = remember { MacroEditorState() }
    val keyListener = remember { applicationContext.getBean<KeyListener>()}

    DisposableEffect(keyListener) {
        keyListener.registerCallback { keyCode, keyChar ->
            macroEditorState.insertSpecialKey(keyCode, keyChar)
        }
        onDispose {
            keyListener.unregisterCallback()
        }
    }

    SelectionPanel(
        onClickSaved = {

        }
    ) {
        KeyClickSelection(formState, macroEditorState, keyListener, modifier)
    }
}

@Composable
fun KeyClickSelection(
    state: KeyClickSelectionState,
    keyboardState: MacroEditorState,
    keyboardListener: KeyListener,
    modifier: Modifier = Modifier
) {
    var isKeyListening by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize(),
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

        Spacer(modifier = Modifier.height(10.dp))

        Column {
            Text(
                text = "Start/Stop Key Listening:",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                TextButton(
                    "Start",
                    onClick = {
                        isKeyListening = true
                        keyboardListener.startListening()
                    }
                )

                TextButton(
                    "Stop",
                    onClick = {
                        isKeyListening = false
                        keyboardListener.stopListening()
                    }
                )
            }
        }

//        TextField(
//            "Keys Recorded:",
//            value = if (readOnly) currentListenedText else currentKeyText,
//            isSingleLine = false,
//            readOnly = readOnly,
//            modifier = Modifier.height(100.dp),
//            onValueChange = { currentKeyText = it },
//        ) {}



        MacroEditor(keyboardState, isKeyListening)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MacroEditor(
    editorState: MacroEditorState,
    isListeningForKeys: Boolean // Pass your recording state here
) {
    LaunchedEffect(editorState.blockToFocus) {
        editorState.blockToFocus?.focusRequester?.requestFocus()
        editorState.blockToFocus = null
    }

    FlowRow(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        editorState.sequence.forEachIndexed { index, item ->
            when (item) {
                is TextBlockUiState -> {
                    BasicTextField(
                        value = item.textValue,
                        onValueChange = { newValue ->
                            if (isListeningForKeys) {
                                if (newValue.text == item.textValue.text) {
                                    item.textValue = newValue
                                }
                            } else {
                                // Normal typing mode
                                item.textValue = newValue
                            }
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                            .defaultMinSize(minWidth = 4.dp)
                            .focusRequester(item.focusRequester)
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) editorState.activeBlockIndex = index
                            }
                            .background(Color.White, RoundedCornerShape(4.dp))
                            .padding(vertical = 4.dp)
                            // --- MAGIC HAPPENS HERE: Catch the Backspace ---
                            .onPreviewKeyEvent { keyEvent ->
                                if (!isListeningForKeys && keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Backspace) {
                                    println("Backspace pressed!")
                                    // If the cursor is at the absolute zero position of this text block
                                    if (item.textValue.selection.start == 0 && item.textValue.selection.end == 0) {

                                        // And the thing before it is a SpecialKeyBlock
                                        if (index > 0 && editorState.sequence[index - 1] is MacroToken.SpecialKeyBlock) {

                                            editorState.removeSpecialKey(index - 1)

                                            // Return true to CONSUME the event so the BasicTextField doesn't
                                            // accidentally delete the character in the previous block!
                                            return@onPreviewKeyEvent true
                                        }
                                    }
                                }
                                false // Let the text field handle normal typing/backspacing
                            }
                    )
                }
                is MacroToken.SpecialKeyBlock -> {
                    // --- MOUSE DELETION UI ---
                    Row(
                        modifier = Modifier
                            .background(Color.LightGray),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.keyLabel, color = Color.Black, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}