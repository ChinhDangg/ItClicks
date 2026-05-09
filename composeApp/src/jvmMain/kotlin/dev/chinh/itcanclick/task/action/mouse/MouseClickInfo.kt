package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.task.Result
import java.awt.Rectangle

data class MouseClickInfo(
    val numClicks: Int,
    val delay: Int,
    override var rect: Rectangle,
    override val isExact: Boolean = false,
    override val executor: MouseAction<MouseClickInfo>,
    override var result: Result?
) : MouseInfo<MouseClickInfo> {

    override fun getSelf(): MouseClickInfo = this
}
