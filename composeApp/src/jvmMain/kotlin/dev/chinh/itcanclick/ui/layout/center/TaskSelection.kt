package dev.chinh.itcanclick.ui.layout.center

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.type.*
import org.springframework.context.ApplicationContext

@Composable
fun TaskSelectionScreen(
    applicationContext: ApplicationContext,
) {
    // State to track which task is currently selected
    var selectedTask by remember { mutableStateOf<TaskType?>(null) }

    // Outer Column: Top Heading + Bottom Main Elements
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary )
    ) {
        // TOP: Narrow Center Heading
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shadowElevation = 2.dp
        ) {
            Text(
                text = "Task Configuration",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        // BOTTOM: Main Elements (Left List + Right Parameters)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Takes up the remaining height
        ) {
            // LEFT COLUMN: Task List
            TaskSelectionList(
                selectedTask = selectedTask,
                onTaskSelected = { selectedTask = it },
                modifier = Modifier
                    .weight(1f) // 1 part width
                    .fillMaxHeight()
                    .padding(8.dp)
            )

            // Vertical Divider separating left and right
            VerticalDivider(color = Color.LightGray, thickness = 1.dp)

            // RIGHT COLUMN: Parameters
            TaskParameterPanel(
                selectedTask = selectedTask,
                applicationContext = applicationContext,
                modifier = Modifier
                    .weight(2f) // 2 parts width (wider for settings)
                    .fillMaxHeight()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun TaskSelectionList(
    selectedTask: TaskType?,
    onTaskSelected: (TaskType) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {

        item { CategoryHeader("Mouse Actions") }
        items(MouseType.entries) { task ->
            TaskListItem(
                task = task,
                isSelected = selectedTask == task,
                onClick = { onTaskSelected(task) }
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item { CategoryHeader("Keyboard Actions") }
        items(KeyType.entries) { task ->
            TaskListItem(
                task = task,
                isSelected = selectedTask == task,
                onClick = { onTaskSelected(task) }
            )
        }
    }
}

@Composable
fun TaskParameterPanel(
    selectedTask: TaskType?,
    applicationContext: ApplicationContext,
    modifier: Modifier = Modifier
) {
    var savedTaskInfo by remember { mutableStateOf<TaskInfo<*>?>(null)}

    Column(modifier = modifier) {
        if (selectedTask == null) {
            // Empty State
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Select a task from the left to configure parameters.", color = Color.Gray)
            }
        } else {
            // Dynamically show the right form based on the task type
            when (selectedTask) {
                is MouseType -> {
                    when (selectedTask) {
                        MouseType.MOUSE_CLICK -> MouseClickEditor(
                            onClick = { savedTaskInfo = it; println(savedTaskInfo) }
                        )
                        MouseType.MOUSE_MOVE -> MouseMoveEditor(
                            onClick = { savedTaskInfo = it; println(savedTaskInfo) },
                            applicationContext = applicationContext
                        )
                        else -> {
                            // Example inputs for a Mouse Task
                            OutlinedTextField(
                                value = "0",
                                onValueChange = {},
                                label = { Text("X Coordinate") },
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = "0",
                                onValueChange = {},
                                label = { Text("Y Coordinate") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
                is KeyType -> {
                    // Example inputs for a Keyboard Task
                    OutlinedTextField(
                        value = "Enter Key...",
                        onValueChange = {},
                        label = { Text("Key Code / Character") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                is ConditionType -> {}
                is WrapperType -> {}
                is OtherType -> {}
            }
        }
    }
}

@Composable
fun CategoryHeader(title: String) {
    Text(
        text = title.uppercase(),
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Composable
fun TaskListItem(
    task: TaskType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.onSecondary
        isHovered -> MaterialTheme.colorScheme.secondary
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick
            )
            .padding(horizontal = 15.dp, vertical = 5.dp)
    ) {
        Text(text = task.displayName, color = MaterialTheme.colorScheme.onBackground, fontSize = 14.sp)
    }
}




@Composable
fun SelectionPanel(
    modifier: Modifier = Modifier,
    onClickSaved: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(bottom = 88.dp), // Space for button
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = content
        )

        // Floating Save Button
        Button(
            onClick = onClickSaved,
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
            ),
            modifier = Modifier
                .height(30.dp)
                .align(Alignment.BottomEnd)
                .pointerHoverIcon(
                    PointerIcon.Hand
                ),
            contentPadding = PaddingValues(
                horizontal = 24.dp,
                vertical = 5.dp
            )
        ) {
            Text("Save")
        }
    }
}

@Composable
fun TextField(
    textContent: String,
    value: String,
    onValueChange: (String) -> Unit,
    content: @Composable () -> Unit
) {
    var focused by remember { mutableStateOf(false) }
    val customSelectionColors = TextSelectionColors(
        handleColor = Color.White,
        backgroundColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f)
    )

    Column {
        Text(
            text = textContent,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(
                LocalTextSelectionColors provides customSelectionColors
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    cursorBrush = SolidColor(Color.White),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(30.dp)
                        .onFocusChanged {
                            focused = it.isFocused
                        }
                        .border(
                            width = 1.dp,
                            color = if (focused)
                                Color(MaterialTheme.colorScheme.onSecondary.value)
                            else
                                MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(6.dp)
                        ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = textContent,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            innerTextField()
                        }
                    }
                )
            }
            content()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextButton(
    displayText: String,
    onClick: () -> Unit,
) {
    var hovered by remember { mutableStateOf(false) }

    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .height(32.dp)
            .width(64.dp)
            .pointerHoverIcon(PointerIcon.Hand)
            .onPointerEvent(PointerEventType.Enter) {
                hovered = true
            }
            .onPointerEvent(PointerEventType.Exit) {
                hovered = false
            },
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (hovered)
                Color(MaterialTheme.colorScheme.secondary.value)
            else
                Color(MaterialTheme.colorScheme.surfaceVariant.value),
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text(
            text = displayText,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun LabeledCheckbox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange(!checked)
            }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null, // handled by Row clickable,
            modifier = Modifier.scale(0.8f),
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.onSecondary,
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 12.sp
        )
    }
}