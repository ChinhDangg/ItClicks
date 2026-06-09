package dev.chinh.itcanclick.ui.layout.side

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chinh.itcanclick.ui.LayoutState
import dev.chinh.itcanclick.ui.layout.bottom.BottomPanelState
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
            HoverableTooltipElement("Main Tasks") {
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
            }

            HoverableTooltipElement("All Tasks") {
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
            }


            Spacer(modifier = Modifier.height(20.dp))

            HoverableTooltipElement("Run") {
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

        }
    })
}

@Composable
fun RightSideVerticalBar(
    layoutState: LayoutState,
) {
    var rightSelectedTab by remember { mutableStateOf(layoutState.activeRightPanel) }
    var showLabel by remember { mutableStateOf(true) }

    SideVerticalBar(showLabel,{
        Column(
            modifier = Modifier.
            padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            HoverableTooltipElement(
                tooltipText = "Edit Current Task",
                position = BarPosition.RIGHT
            ) {
                IconButton(
                    icon = Res.drawable.check_box,
                    label = "Current",
                    isSelected = rightSelectedTab == RightPanelState.CURRENT,
                    showLabel = showLabel,
                    onClick = {
                        rightSelectedTab = RightPanelState.CURRENT
                        layoutState.toggleRightPanel(RightPanelState.CURRENT)
                    }
                )
            }
        }
    })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HoverableTooltipElement(
    tooltipText: String,
    position: BarPosition = BarPosition.LEFT,
    content: @Composable () -> Unit
) {
    val componentPlacement = when (position) {
        BarPosition.LEFT -> TooltipPlacement.ComponentRect(
            anchor = Alignment.CenterEnd,
            alignment = Alignment.CenterEnd,
            offset = DpOffset(5.dp, 0.dp)
        )
        BarPosition.RIGHT -> TooltipPlacement.ComponentRect(
            anchor = Alignment.CenterStart,
            alignment = Alignment.CenterStart,
            offset = DpOffset((-5).dp, 0.dp)
        )
        BarPosition.TOP -> TooltipPlacement.ComponentRect(
            anchor = Alignment.BottomCenter,
            alignment = Alignment.TopCenter,
            offset = DpOffset(0.dp, 5.dp)
        )
        BarPosition.BOTTOM -> TooltipPlacement.ComponentRect(
            anchor = Alignment.TopCenter,
            alignment = Alignment.BottomCenter,
            offset = DpOffset(0.dp, (-5).dp)
        )
    }

    TooltipArea(
        tooltip = {
            Surface(
                modifier = Modifier
                    .padding(4.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = tooltipText,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp,
                    lineHeight = 10.sp,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 8.dp)
                )
            }
        },
        delayMillis = 500,
        tooltipPlacement = componentPlacement
    ) {
        content()
    }
}

@Composable
fun IconButton(
    icon: DrawableResource,
    label: String,
    isSelected: Boolean,
    showLabel: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(), // Ensures the clickable area spans the width of the sidebar
    iconModifier: Modifier = Modifier.size(18.dp)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
    val backgroundColor = when {
        isSelected -> Color(MaterialTheme.colorScheme.onSecondary.value)
        isHovered -> MaterialTheme.colorScheme.secondary
        else -> Color.Transparent
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            // Clip applies the rounded shape to the background and the click ripple effect
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick
            )
            .padding(5.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = contentColor,
            modifier = iconModifier
        )

        if (showLabel) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = label,
                color = contentColor,
                fontSize = 10.sp, // Smaller font looks cleaner in narrow sidebars
                lineHeight = 10.sp,
                maxLines = 2
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