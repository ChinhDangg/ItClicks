package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.Task
import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.TaskType
import dev.chinh.itcanclick.task.action.Action
import dev.chinh.itcanclick.task.action.ActionInfo
import dev.chinh.itcanclick.task.condition.ConditionResult
import java.awt.Rectangle

data class MouseInfo(
    var rect: Rectangle,
    val isExact: Boolean = false,
    val delay: Int = 50,
    val numClicks: Int = 1,
    val type: TaskType,
    val taskExecutor: Action<MouseInfo>
) : ActionInfo<MouseInfo>(type, taskExecutor) {

    override fun passResult(result: Result) {
        super.passResult(result)
        (result as? ConditionResult)?.let {
            this.rect = it.rect
        }
    }

    override fun getSelf(): MouseInfo = this
}