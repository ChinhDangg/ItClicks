package dev.chinh.itcanclick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopHorizontalBar(
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