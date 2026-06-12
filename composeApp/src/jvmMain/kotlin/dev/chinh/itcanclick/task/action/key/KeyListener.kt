package dev.chinh.itcanclick.task.action.key

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import dev.chinh.itcanclick.log.log
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service


@Component
class GlobalKeystrokeState {
    // Hold the last 10 characters typed to prevent memory leaks
    private val maxBufferSize = 100

    private val _typedText = MutableStateFlow("")
    val typedText = _typedText.asStateFlow()

    fun appendCharacter(char: String) {
        _typedText.value = (_typedText.value + char).takeLast(maxBufferSize)
    }

    fun clear() {
        _typedText.value = ""
    }
}

@Service
class KeyListener(
    private val keystrokeState: GlobalKeystrokeState
): NativeKeyListener {

    private var isListening = false

    @PostConstruct
    fun initHook() {
        try {
            GlobalScreen.registerNativeHook()
        } catch (e: NativeHookException) {
            log("Failed to register native hook: $e")
        }
    }

    @PreDestroy
    fun destroyHook() {
        try {
            unregister()
            GlobalScreen.unregisterNativeHook()
        } catch (e: NativeHookException) {
            log("Failed to unregister native hook: $e")
        }
    }

    fun register() {
        if (isListening) return
        GlobalScreen.addNativeKeyListener(this)
    }

    fun unregister() {
        GlobalScreen.removeNativeKeyListener(this)
    }

    override fun nativeKeyTyped(keyEvent: NativeKeyEvent) {
        val keyCode = keyEvent.keyCode
        val charTyped = keyEvent.keyChar.toString()

        if (keyCode == NativeKeyEvent.VC_F12) {
            unregister()
        }
        keystrokeState.appendCharacter(charTyped)
    }

    override fun nativeKeyPressed(keyEvent: NativeKeyEvent?) {
    }

    override fun nativeKeyReleased(keyEvent: NativeKeyEvent?) {
    }

}