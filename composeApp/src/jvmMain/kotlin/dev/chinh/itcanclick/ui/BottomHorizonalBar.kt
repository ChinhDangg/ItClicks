package dev.chinh.itcanclick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomHorizonalBar() {
    Row(Modifier.fillMaxWidth().height(25.dp).background(Color(MaterialTheme.colorScheme.background.value))) {
        Text("Ready", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))
    }
}