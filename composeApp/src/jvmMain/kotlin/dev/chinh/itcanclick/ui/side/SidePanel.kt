package dev.chinh.itcanclick.ui.side

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chinh.itcanclick.ui.LayoutState

@Composable
fun LeftSidePanel(
    layoutState: LayoutState
) {
    SidePanel {

    }
}

@Composable
fun RightSidePanel(
    layoutState: LayoutState
) {
    SidePanel {

    }
}

@Composable
fun SidePanel(
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