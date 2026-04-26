package dev.chinh.itcanclick

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ConfigurableApplicationContext
import kotlin.jvm.java

@SpringBootApplication
class ItCanClickApplication

fun main(args: Array<String>) = application {
    // Start the Spring Boot Application Context first
    // headless(false) is strictly required for Compose/AWT to render windows
    val applicationContext: ConfigurableApplicationContext =
        SpringApplicationBuilder(ItCanClickApplication::class.java)
            .headless(false)
            .run(*args)

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
