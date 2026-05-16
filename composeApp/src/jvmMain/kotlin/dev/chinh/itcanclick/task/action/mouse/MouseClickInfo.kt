package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.data.action.mouse.MouseClickData
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.type.MouseType
import java.awt.Rectangle

data class MouseClickInfo(
    val numClicks: Int,
    val delay: Int,
    override var rect: Rectangle,
    override val isExact: Boolean = false,
    override val id: String,
    override val name: String,
    override var result: Result?
) : MouseInfo<MouseClickInfo> {

    override val taskType: MouseType = MouseType.MOUSE_CLICK

    override fun getSelf(): MouseClickInfo = this

    override fun getTaskData(): MouseClickData {
        return MouseClickData(
            numClicks, rect, isExact, delay, id, name
        )
    }
}
