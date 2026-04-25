package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.task.action.ActionInfo
import java.awt.Rectangle

data class MouseInfo(
    var rect: Rectangle,
    var isExact: Boolean = false,
    var delay: Int = 50,
    var numClicks: Int = 1
) : ActionInfo