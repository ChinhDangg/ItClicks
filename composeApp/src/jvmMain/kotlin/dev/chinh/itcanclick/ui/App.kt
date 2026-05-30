package dev.chinh.itcanclick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.SplitterScope
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import org.jetbrains.skiko.Cursor


@Composable
fun WindowScope.App(exitApplication: () -> Unit) {
    var isDarkTheme by remember { mutableStateOf(true) }

    AppTheme(isDarkTheme = isDarkTheme) {
        Column(Modifier.fillMaxSize()) {
            WindowDraggableArea {
                TopHorizontalBar(exitApplication)
            }
            mainLayout()
        }
    }
}

@Composable
fun AppTheme(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val currentColorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = currentColorScheme,
        content = content
    )
}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
@Preview
fun mainLayout() {
    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            Modifier
                .fillMaxWidth()
                .background(Color(MaterialTheme.colorScheme.background.value))
                .weight(1f)
        ) {

            // Left Tool Window Bar (The narrow strip with vertical buttons)
            LeftSideVerticalBar()

            val verticalSplitterState = rememberSplitPaneState(initialPositionPercentage = 0.75f)
            VerticalSplitPane(
                splitPaneState = verticalSplitterState,
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                // --- TOP SECTION PANEL (Left + Center + Right) ---
                first(minSize = 50.dp) {

                    // Left-to-Center splitter
                    val leftSplitterState = rememberSplitPaneState(initialPositionPercentage = 0.2f)
                    HorizontalSplitPane(
                        splitPaneState = leftSplitterState,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    ) {
                        first(minSize = 50.dp) {
                            // Left Panel (e.g., Project Explorer)
                            SidePanel() {}
                        }
                        second(minSize = 100.dp) {
                            val rightSplitterState = rememberSplitPaneState(initialPositionPercentage = 0.75f)
                            HorizontalSplitPane(
                                splitPaneState = rightSplitterState,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                first(minSize = 50.dp) {
                                    // Main Content (e.g., Code Editor)
                                    CenterPanel() {}
                                }
                                second(minSize = 50.dp) {
                                    // Right Panel (e.g., Debug Console)
                                    SidePanel() {}
                                }
                                splitter {
                                    horizontalPaneSplitter()
                                }
                            }
                        }
                        // Custom visual for the drag handle
                        splitter {
                            horizontalPaneSplitter()
                        }
                    }
                }

                second(minSize = 50.dp) {
                    BottomPanel() {}
                }

                // Custom visual for the drag handle
                splitter {
                    verticalPaneSplitter()
                }
            }

            // Right Tool Window Bar (The narrow strip with vertical buttons)
            RightSideVerticalBar()
        }

        // BOTTOM STATUS BAR
        BottomHorizonalBar()
    }
}


@OptIn(ExperimentalSplitPaneApi::class)
fun SplitterScope.verticalPaneSplitter() {
    visiblePart {
        Box(
            Modifier
                .height(5.dp)
                .fillMaxWidth()
                .background(Color.Transparent)
        )
    }
    handle {
        Box(
            Modifier
                .markAsHandle()
                .pointerHoverIcon(PointerIcon(Cursor(Cursor.S_RESIZE_CURSOR)))
                .height(10.dp)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalSplitPaneApi::class)
fun SplitterScope.horizontalPaneSplitter() {
    visiblePart {
        Box(
            Modifier
                .width(5.dp)
                .fillMaxHeight()
                .background(Color.Transparent)
        )
    }
    handle {
        Box(
            Modifier
                .markAsHandle()
                .pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))
                .width(10.dp)
                .fillMaxHeight()
        )
    }
}