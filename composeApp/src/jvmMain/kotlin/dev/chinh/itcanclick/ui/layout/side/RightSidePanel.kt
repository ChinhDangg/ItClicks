package dev.chinh.itcanclick.ui.layout.side

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chinh.itcanclick.ui.LayoutState
import itcanclick.composeapp.generated.resources.Res
import itcanclick.composeapp.generated.resources.list
import itcanclick.composeapp.generated.resources.plus
import itcanclick.composeapp.generated.resources.trash

@Composable
fun RightSidePanel(
    layoutState: LayoutState
) {
    CurrentTaskPanel()
}

@Composable
fun CurrentTaskPanel() {
    SidePanel {
        Column {
            Text(
                text = "Current Task:",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 5.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp)

            ) {
                IconButton(
                    icon = Res.drawable.plus,
                    label = "New Task",
                    isSelected = false,
                    showLabel = false,
                    onClick = { },
                    modifier = Modifier,
                    iconModifier = Modifier.size(13.dp)
                )
                IconButton(
                    icon = Res.drawable.trash,
                    label = "Delete Task",
                    isSelected = false,
                    showLabel = false,
                    onClick = { },
                    modifier = Modifier,
                    iconModifier = Modifier.size(13.dp)
                )
                IconButton(
                    icon = Res.drawable.list,
                    label = "Add to List",
                    isSelected = false,
                    showLabel = false,
                    onClick = { },
                    modifier = Modifier,
                    iconModifier = Modifier.size(13.dp)
                )
            }

        }
    }
}