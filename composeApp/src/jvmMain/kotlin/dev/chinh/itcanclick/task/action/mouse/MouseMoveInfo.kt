package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.data.action.mouse.MouseMoveData
import dev.chinh.itcanclick.perform.TaskRegisterService
import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.condition.ConditionResult
import dev.chinh.itcanclick.task.type.MouseType
import java.awt.Rectangle

data class MouseMoveInfo(
    var rect: Rectangle,
    val isExact: Boolean,
    override val name: String,
    override val id: String = TaskRegisterService.generateId()
) : MouseInfo<MouseMoveInfo> {

    override val taskType: MouseType = MouseType.MOUSE_MOVE
    override var result: Result? = null

    override fun getSelf(): MouseMoveInfo = this

    override fun passResult(result: Result) {
        super.passResult(result)
        (result as? ConditionResult)?.let {
            this.rect = it.rect
        }
    }

    override fun getTaskData(): MouseMoveData {
        return MouseMoveData(rect, isExact, id, name)
    }
}
