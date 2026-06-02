package dev.chinh.itcanclick.ui.side

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chinh.itcanclick.ui.LayoutState
import dev.chinh.itcanclick.ui.bottom.BottomPanelState
import itcanclick.composeapp.generated.resources.Res
import itcanclick.composeapp.generated.resources.add
import itcanclick.composeapp.generated.resources.check_box
import itcanclick.composeapp.generated.resources.folder
import itcanclick.composeapp.generated.resources.play
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun LeftSideVerticalBar(
    layoutState: LayoutState,
) {
    var leftSelectedTab by remember { mutableStateOf(layoutState.activeLeftPanel) }
    var showLabel by remember { mutableStateOf(true) }

    SideVerticalBar(showLabel,{
        Column(
            modifier = Modifier.
                padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            IconButton(
                icon = Res.drawable.folder,
                label = "Main",
                isSelected = leftSelectedTab == LeftPanelState.MAIN,
                showLabel = showLabel,
                onClick = {
                    leftSelectedTab = LeftPanelState.MAIN
                    layoutState.toggleLeftPanel(LeftPanelState.MAIN)
                }
            )

            IconButton(
                icon = Res.drawable.add,
                label = "All",
                isSelected = leftSelectedTab == LeftPanelState.ALL,
                showLabel = showLabel,
                onClick = {
                    leftSelectedTab = LeftPanelState.ALL
                    layoutState.toggleLeftPanel(LeftPanelState.ALL)
                }
            )


            IconButton(
                icon = Res.drawable.check_box,
                label = "Edit",
                isSelected = leftSelectedTab == LeftPanelState.EDIT,
                showLabel = showLabel,
                onClick = {
                    leftSelectedTab = LeftPanelState.EDIT
                    layoutState.toggleLeftPanel(LeftPanelState.EDIT)
                }
            )


            Spacer(modifier = Modifier.height(20.dp))

            IconButton(
                icon = Res.drawable.play,
                label = "Run",
                isSelected = layoutState.activeBottomPanel == BottomPanelState.RUN,
                showLabel = showLabel,
                onClick = {
                    layoutState.toggleBottomPanel(BottomPanelState.RUN)
                }
            )
            
        }
    })
}

@Composable
fun RightSideVerticalBar() {
    var showLabel by remember { mutableStateOf(true) }
    SideVerticalBar(showLabel,{

    })
}

@Composable
fun IconButton(
    icon: DrawableResource,
    label: String,
    isSelected: Boolean,
    showLabel: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSecondary
    val backgroundColor = if (isSelected) Color(0xFF7E57C2) else Color.Transparent

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            // Clip applies the rounded shape to the background and the click ripple effect
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(5.dp)
            .fillMaxWidth() // Ensures the clickable area spans the width of the sidebar
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        if (showLabel) {
            Text(
                text = label,
                color = contentColor,
                fontSize = 10.sp, // Smaller font looks cleaner in narrow sidebars
                lineHeight = 10.sp,
                maxLines = 1
            )
        }
    }
}


@Composable
fun SideVerticalBar(
    expand: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val width = if (expand) 60.dp else 40.dp

    Column(
        Modifier
            .width(width)
            .fillMaxHeight()
            .background(
                color = Color(MaterialTheme.colorScheme.background.value),
            )
    ) {
        /* Vertical rotated text buttons go here */
        content()
    }
}