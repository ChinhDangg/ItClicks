package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.condition.ConditionResult
import java.awt.Rectangle

data class MouseBaseInfo(
    val delay: Int,
    override var rect: Rectangle,
    override val isExact: Boolean = false,
    override val executor: MouseAction<MouseBaseInfo>,
    override var result: Result?
) : MouseInfo<MouseBaseInfo> {

    override fun getSelf(): MouseBaseInfo = this
}