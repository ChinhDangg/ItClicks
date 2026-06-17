package dev.chinh.itcanclick.task.action.key

import androidx.compose.runtime.mutableStateListOf
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

class MacroEditorState {
    val sequence = mutableStateListOf<MacroToken>(MacroToken.TextBlock(""))

    // Called when the text field content is edited by the user
    fun updateText(index: Int, newText: String) {
        val current = sequence[index]
        if (current is MacroToken.TextBlock) {
            sequence[index] = current.copy(text = newText)
        }
    }

    // Called when JNativeHook listener detects F1, Enter, Tab, etc.
    fun insertSpecialKey(keyCode: Int, keyLabel: String) {
        // 1. Add the special key chip
        sequence.add(MacroToken.SpecialKeyBlock(keyCode, keyLabel))
        // 2. Add a new empty text block after it so the user can keep typing!
        sequence.add(MacroToken.TextBlock(""))
    }
}


@Service
class KeyListener: NativeKeyListener {

    val keyListenedCallback: ((String, Int) -> Unit)? = null

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
        keyListenedCallback?.invoke(charTyped, keyCode)
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