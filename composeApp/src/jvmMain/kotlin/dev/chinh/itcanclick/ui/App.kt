package dev.chinh.itcanclick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import dev.chinh.itcanclick.ui.bottom.BottomPanel
import dev.chinh.itcanclick.ui.bottom.BottomPanelState
import dev.chinh.itcanclick.ui.side.*
import org.jetbrains.compose.splitpane.*
import org.jetbrains.skiko.Cursor


class LayoutState {
    var activeLeftPanel by mutableStateOf(LeftPanelState.NONE)
        private set
    var activeRightPanel by mutableStateOf(RightPanelState.NONE)
        private set

    var activeBottomPanel by mutableStateOf(BottomPanelState.NONE)
        private set

    fun toggleLeftPanel(panel: LeftPanelState) {
        activeLeftPanel = if (activeLeftPanel == panel) LeftPanelState.NONE else panel
    }

    fun toggleRightPanel(panel: RightPanelState) {
        activeRightPanel = if (activeRightPanel == panel) RightPanelState.NONE else panel
    }

    fun toggleBottomPanel(panel: BottomPanelState) {
        activeBottomPanel = if (activeBottomPanel == panel) BottomPanelState.NONE else panel
    }
}

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
    val layoutState = remember { LayoutState() }

    val leftSplitterState = rememberSplitPaneState(initialPositionPercentage = 0.2f)
    val rightSplitterState = rememberSplitPaneState(initialPositionPercentage = 0.75f)
    val bottomSplitterState = rememberSplitPaneState(initialPositionPercentage = 0.75f)

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color(MaterialTheme.colorScheme.background.value))
                .weight(1f)
        ) {
            // Left Tool Window Bar (The narrow strip with vertical buttons)
            LeftSideVerticalBar(layoutState)

            VerticalSplitLayout(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                layoutState.activeBottomPanel != BottomPanelState.NONE,
                bottomSplitterPane = bottomSplitterState,
                topContent = {
                    HorizontalSplitLayout(
                        modifier = Modifier.fillMaxSize(),
                        layoutState.activeLeftPanel != LeftPanelState.NONE,
                        layoutState.activeRightPanel != RightPanelState.NONE,
                        leftSplitterState = leftSplitterState,
                        rightSplitterState = rightSplitterState,
                        leftContent = { LeftSidePanel(layoutState) },
                        centerContent = { CenterPanel() {} },
                        rightContent = { RightSidePanel(layoutState) }
                    )
                },
                bottomContent = { BottomPanel(layoutState) }
            )

            // Right Tool Window Bar (The narrow strip with vertical buttons)
            RightSideVerticalBar()
        }

        // BOTTOM STATUS BAR
        BottomHorizonalBar()
    }
}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun VerticalSplitLayout(
    modifier: Modifier = Modifier,
    isBottomOpen: Boolean,
    bottomSplitterPane: SplitPaneState,
    topContent: @Composable () -> Unit,
    bottomContent: @Composable () -> Unit
) {
    if (isBottomOpen) {
        VerticalSplitPane(
            splitPaneState = bottomSplitterPane,
            modifier = modifier
        ) {
            first(minSize = 50.dp) { topContent() }
            second(minSize = 50.dp) { bottomContent() }
            // Custom visual for the drag handle
            splitter { verticalPaneSplitter() }
        }
    } else {
        Box(modifier = modifier) {
            topContent()
        }
    }
}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun HorizontalSplitLayout(
    modifier: Modifier = Modifier,
    isLeftOpen: Boolean,
    isRightOpen: Boolean,
    leftSplitterState: SplitPaneState,
    rightSplitterState: SplitPaneState,
    leftContent: @Composable () -> Unit,
    centerContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit
) {
    if (isLeftOpen && isRightOpen) {
        // Left-to-Center splitter
        HorizontalSplitPane(
            splitPaneState = leftSplitterState,
            modifier = modifier
        ) {
            first(minSize = 50.dp) { leftContent() }
            second(minSize = 100.dp) {
                HorizontalSplitPane(
                    splitPaneState = rightSplitterState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    first(minSize = 50.dp) { centerContent() }
                    second(minSize = 50.dp) { rightContent() }
                    splitter { horizontalPaneSplitter() }
                }
            }
            splitter { horizontalPaneSplitter() }
        }
    } else if (isLeftOpen && !isRightOpen) {
        HorizontalSplitPane(splitPaneState = leftSplitterState, modifier = modifier) {
            first(minSize = 50.dp) { leftContent() }
            second(minSize = 100.dp) { centerContent() }
            splitter { horizontalPaneSplitter() }
        }
    } else if (!isLeftOpen && isRightOpen) {
        HorizontalSplitPane(splitPaneState = rightSplitterState, modifier = modifier) {
            first(minSize = 100.dp) { centerContent() }
            second(minSize = 50.dp) { rightContent() }
            splitter { horizontalPaneSplitter() }
        }
    } else {
        Box(modifier = modifier) { centerContent() }
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