package dev.chinh.itcanclick.ui.layout.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chinh.itcanclick.ui.LayoutState

@Composable
fun BottomPanel(
    layoutState: LayoutState,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                Color(MaterialTheme.colorScheme.surface.value),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {

    }
}