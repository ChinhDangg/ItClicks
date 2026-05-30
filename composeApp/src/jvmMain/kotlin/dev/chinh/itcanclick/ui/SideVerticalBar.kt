package dev.chinh.itcanclick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import itcanclick.composeapp.generated.resources.Res
import itcanclick.composeapp.generated.resources.folder
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun LeftSideVerticalBar() {
    SideVerticalBar({
        Column {
            IconButton(
                icon = Res.drawable.folder,
                label = "Folder",
                isSelected = true,
                onClick = {}
            )
        }
    })
}

@Composable
fun RightSideVerticalBar() {
    SideVerticalBar({

    })
}

@Composable
fun IconButton(
    icon: DrawableResource,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Gray

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            // Clip applies the rounded shape to the background and the click ripple effect
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .fillMaxWidth() // Ensures the clickable area spans the width of the sidebar
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = contentColor,
            fontSize = 12.sp, // Smaller font looks cleaner in narrow sidebars
            maxLines = 1
        )
    }
}


@Composable
fun SideVerticalBar(
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