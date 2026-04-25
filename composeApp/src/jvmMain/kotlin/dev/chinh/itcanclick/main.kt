package dev.chinh.itcanclick

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val state = rememberWindowState(
        size = DpSize(400.dp, 350.dp)
    )

    Window(
        onCloseRequest = ::exitApplication,
        undecorated = true,
        title = "It Can Click",
        state = state,
        alwaysOnTop = true
    ) {
        App(::exitApplication)
    }
}
