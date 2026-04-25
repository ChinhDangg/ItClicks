package dev.chinh.itcanclick

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    Column(Modifier.fillMaxSize()) {
        WindowDraggableArea {
            topHorizontalBar(exitApplication)
        }
        mainLayout()
    }
}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
@Preview
fun mainLayout() {
    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFF26282b))
                .weight(1f)
        ) {

            // Left Tool Window Bar (The narrow strip with vertical buttons)
            sideVerticalBar() {}

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
                            sidePanel() {}
                        }
                        second(minSize = 100.dp) {
                            val rightSplitterState = rememberSplitPaneState(initialPositionPercentage = 0.75f)
                            HorizontalSplitPane(
                                splitPaneState = rightSplitterState,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                first(minSize = 50.dp) {
                                    // Main Content (e.g., Code Editor)
                                    centerPanel() {}
                                }
                                second(minSize = 50.dp) {
                                    // Right Panel (e.g., Debug Console)
                                    sidePanel() {}
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
                    bottomPanel() {}
                }

                // Custom visual for the drag handle
                splitter {
                    verticalPaneSplitter()
                }
            }

            // Right Tool Window Bar (The narrow strip with vertical buttons)
            sideVerticalBar() {}
        }

        // 3. BOTTOM STATUS BAR
        Row(Modifier.fillMaxWidth().height(25.dp).background(Color(0xFF26282b))) {
            Text("Ready", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}


@Composable
fun sideVerticalBar(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        Modifier
            .width(40.dp)
            .fillMaxHeight()
            .background(
                color = Color(0xFF26282b)
            )
    ) {
        /* Vertical rotated text buttons go here */
        content()
    }
}

@Composable
fun sidePanel(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(
                Color(0xFF191a1c),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        content()
    }
}

@Composable
fun centerPanel(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                Color(0xFF1e1f22),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        Text("Center Panel")
        content()
    }
}

@Composable
fun bottomPanel(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                Color(0xFF191a1c),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {
        content()
    }
}

@Composable
fun topHorizontalBar(
    exitApplication: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color(0xFF26282b)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Menu Items
        Text(
            text = "File  Edit  View  Navigate",
            color = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Pushes the window controls to the far right
        Spacer(modifier = Modifier.weight(1f))

        // 3. RECREATE WINDOW CONTROLS (Close Button)
        IconButton(onClick = exitApplication) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }
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