package dev.chinh.itcanclick.task.action.key

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import dev.chinh.itcanclick.log.log
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import kotlinx.serialization.Serializable
import org.springframework.stereotype.Service
import java.awt.event.KeyEvent


@Serializable
sealed interface MacroToken {

    // Represents normal text the user types (e.g., "Hello", "my password123")
    @Serializable
    data class TextBlock(val text: String) : MacroToken

    // Represents a special JNativeHook key (e.g., Tab, F1, Enter)
    @Serializable
    data class SpecialKeyBlock(val nativeKeyCode: Int, val keyLabel: String) : MacroToken
}

class TextBlockUiState(initialText: String = "") {
    // Holds the text AND the exact cursor position
    var textValue by mutableStateOf(
        TextFieldValue(text = initialText, selection = TextRange(initialText.length))
    )

    // Allows us to programmatically force the keyboard focus here
    val focusRequester = FocusRequester()
}

class MacroEditorState {
    // The working sequence mixes TextBlockUiState and MacroToken.SpecialKeyBlock
    val sequence = mutableStateListOf<Any>(TextBlockUiState("Some text"))

    // Tracks which text block currently has the user's cursor
    var activeBlockIndex by mutableStateOf(0)

    // Tracks when we need to force focus on a newly created block
    var blockToFocus by mutableStateOf<TextBlockUiState?>(null)

    fun insertSpecialKey(keyCode: Int, keyLabel: String) {
        val index = activeBlockIndex
        val currentBlock = sequence.getOrNull(index) as? TextBlockUiState ?: return

        val fullText = currentBlock.textValue.text
        val cursor = currentBlock.textValue.selection.start.coerceIn(0, fullText.length)

        // Split the text at the cursor
        val textBefore = fullText.substring(0, cursor)
        val textAfter = fullText.substring(cursor)

        // Update the current block to only contain the text BEFORE the cursor
        currentBlock.textValue = currentBlock.textValue.copy(
            text = textBefore,
            selection = TextRange(textBefore.length)
        )

        // Create the Special Key Chip
        val newSpecialKey = MacroToken.SpecialKeyBlock(keyCode, keyLabel)

        // Create a NEW text block containing the text AFTER the cursor
        val newTextBlock = TextBlockUiState(textAfter)
        // Put the cursor at the very beginning of this new block
        newTextBlock.textValue = newTextBlock.textValue.copy(selection = TextRange(0))

        // Insert them into the sequence
        sequence.add(index + 1, newSpecialKey)
        sequence.add(index + 2, newTextBlock)

        // Update the active block and queue it for auto-focus
        activeBlockIndex = index + 2
        blockToFocus = newTextBlock
    }

    fun removeSpecialKey(chipIndex: Int) {
        // 1. Verify the target is actually a special key
        if (sequence.getOrNull(chipIndex) !is MacroToken.SpecialKeyBlock) return

        val beforeBlock = sequence.getOrNull(chipIndex - 1) as? TextBlockUiState
        val afterBlock = sequence.getOrNull(chipIndex + 1) as? TextBlockUiState

        if (beforeBlock != null && afterBlock != null) {
            // 2. We are merging the text AFTER the chip into the text BEFORE the chip
            val beforeText = beforeBlock.textValue.text
            val afterText = afterBlock.textValue.text

            // The cursor should end up exactly where the chip used to be
            val newCursorPos = beforeText.length

            beforeBlock.textValue = TextFieldValue(
                text = beforeText + afterText,
                selection = TextRange(newCursorPos)
            )

            // 3. Remove the now-redundant afterBlock, then the Chip itself
            // (Always remove the higher index first so the lower index doesn't shift!)
            sequence.removeAt(chipIndex + 1)
            sequence.removeAt(chipIndex)

            // 4. Force the keyboard focus back to the merged block
            activeBlockIndex = chipIndex - 1
            blockToFocus = beforeBlock
        } else {
            // Fallback just in case it's at the absolute edge (rare)
            sequence.removeAt(chipIndex)
        }
    }
}


@Service
class KeyListener: NativeKeyListener {

    var keyListenedCallback: ((Int, String) -> Unit)? = null

    private var isListening = false

    @PostConstruct
    fun initHook() {
        try {
            try {
                GlobalScreen.registerNativeHook()
            } catch (e: NativeHookException) {
                log("Failed to register native hook: $e")
            }
        } catch (e: NativeHookException) {
            log("Failed to register native hook: $e")
        }
    }

    @PreDestroy
    fun destroyHook() {
        try {
            stopListening()
            GlobalScreen.unregisterNativeHook()
        } catch (e: NativeHookException) {
            log("Failed to unregister native hook: $e")
        }
    }

    fun registerCallback(callback: (Int, String) -> Unit) {
        this.keyListenedCallback = callback
    }

    fun unregisterCallback() {
        keyListenedCallback = null
    }

    fun startListening() {
        if (isListening) {
            log("Already listening")
            return
        }
        log("Start listening")
        GlobalScreen.addNativeKeyListener(this)
        isListening = true
    }

    fun stopListening() {
        log("Stop listening")
        GlobalScreen.removeNativeKeyListener(this)
        isListening = false
    }

    override fun nativeKeyTyped(keyEvent: NativeKeyEvent) {
    }

    override fun nativeKeyPressed(keyEvent: NativeKeyEvent) {
    }

    override fun nativeKeyReleased(keyEvent: NativeKeyEvent) {
        val keyCode = keyEvent.keyCode
        val charTyped = NativeKeyEvent.getKeyText(keyCode)

        if (keyCode == NativeKeyEvent.VC_F12) {
            stopListening()
        }
        println("Key released: $charTyped")
        keyListenedCallback?.invoke(keyCode, charTyped)
    }
}

fun stringToRobotKeyCodes(key: String): List<Int> {
    val lowerCaseKey = key.lowercase()
    return lowerCaseKey.mapNotNull {
        val keyCode = KeyEvent.getExtendedKeyCodeForChar(it.code)
        if (keyCode == KeyEvent.CHAR_UNDEFINED.code)
            null
        else
            keyCode
    }
}

fun Int.fromNativeKeyToAwtKeyCode(): Int? {
    return when (this) {
        // --- Modifiers ---
        NativeKeyEvent.VC_SHIFT -> KeyEvent.VK_SHIFT
        NativeKeyEvent.VC_CONTROL -> KeyEvent.VK_CONTROL
        NativeKeyEvent.VC_ALT -> KeyEvent.VK_ALT
        NativeKeyEvent.VC_META -> KeyEvent.VK_META

        // --- Action Keys ---
        NativeKeyEvent.VC_ENTER -> KeyEvent.VK_ENTER
        NativeKeyEvent.VC_ESCAPE -> KeyEvent.VK_ESCAPE
        NativeKeyEvent.VC_TAB -> KeyEvent.VK_TAB
        NativeKeyEvent.VC_BACKSPACE -> KeyEvent.VK_BACK_SPACE
        NativeKeyEvent.VC_SPACE -> KeyEvent.VK_SPACE
        NativeKeyEvent.VC_UP -> KeyEvent.VK_UP
        NativeKeyEvent.VC_DOWN -> KeyEvent.VK_DOWN
        NativeKeyEvent.VC_LEFT -> KeyEvent.VK_LEFT
        NativeKeyEvent.VC_RIGHT -> KeyEvent.VK_RIGHT

        // --- Function Keys ---
        NativeKeyEvent.VC_F1 -> KeyEvent.VK_F1
        NativeKeyEvent.VC_F2 -> KeyEvent.VK_F2
        NativeKeyEvent.VC_F3 -> KeyEvent.VK_F3
        NativeKeyEvent.VC_F4 -> KeyEvent.VK_F4
        NativeKeyEvent.VC_F5 -> KeyEvent.VK_F5
        NativeKeyEvent.VC_F6 -> KeyEvent.VK_F6
        NativeKeyEvent.VC_F7 -> KeyEvent.VK_F7
        NativeKeyEvent.VC_F8 -> KeyEvent.VK_F8
        NativeKeyEvent.VC_F9 -> KeyEvent.VK_F9

        // --- Letters ---
        NativeKeyEvent.VC_A -> KeyEvent.VK_A
        NativeKeyEvent.VC_B -> KeyEvent.VK_B
        NativeKeyEvent.VC_C -> KeyEvent.VK_C
        NativeKeyEvent.VC_D -> KeyEvent.VK_D
        NativeKeyEvent.VC_E -> KeyEvent.VK_E
        NativeKeyEvent.VC_F -> KeyEvent.VK_F
        NativeKeyEvent.VC_G -> KeyEvent.VK_G
        NativeKeyEvent.VC_H -> KeyEvent.VK_H
        NativeKeyEvent.VC_I -> KeyEvent.VK_I
        NativeKeyEvent.VC_J -> KeyEvent.VK_J
        NativeKeyEvent.VC_K -> KeyEvent.VK_K
        NativeKeyEvent.VC_L -> KeyEvent.VK_L
        NativeKeyEvent.VC_M -> KeyEvent.VK_M
        NativeKeyEvent.VC_N -> KeyEvent.VK_N
        NativeKeyEvent.VC_O -> KeyEvent.VK_O
        NativeKeyEvent.VC_P -> KeyEvent.VK_P
        NativeKeyEvent.VC_Q -> KeyEvent.VK_Q
        NativeKeyEvent.VC_R -> KeyEvent.VK_R
        NativeKeyEvent.VC_S -> KeyEvent.VK_S
        NativeKeyEvent.VC_T -> KeyEvent.VK_T
        NativeKeyEvent.VC_U -> KeyEvent.VK_U
        NativeKeyEvent.VC_V -> KeyEvent.VK_V
        NativeKeyEvent.VC_W -> KeyEvent.VK_W
        NativeKeyEvent.VC_X -> KeyEvent.VK_X
        NativeKeyEvent.VC_Y -> KeyEvent.VK_Y
        NativeKeyEvent.VC_Z -> KeyEvent.VK_Z

        // --- Numbers ---
        NativeKeyEvent.VC_1 -> KeyEvent.VK_1
        NativeKeyEvent.VC_2 -> KeyEvent.VK_2
        NativeKeyEvent.VC_3 -> KeyEvent.VK_3
        NativeKeyEvent.VC_4 -> KeyEvent.VK_4
        NativeKeyEvent.VC_5 -> KeyEvent.VK_5
        NativeKeyEvent.VC_6 -> KeyEvent.VK_6
        NativeKeyEvent.VC_7 -> KeyEvent.VK_7
        NativeKeyEvent.VC_8 -> KeyEvent.VK_8
        NativeKeyEvent.VC_9 -> KeyEvent.VK_9
        NativeKeyEvent.VC_0 -> KeyEvent.VK_0

        else -> null // Key not mapped
    }
}