package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.action.ActionInfo
import dev.chinh.itcanclick.task.condition.ConditionResult
import java.awt.Rectangle

interface MouseInfo<M : MouseInfo<M>> : ActionInfo<M> {

    var rect: Rectangle
    val isExact: Boolean

    override fun passResult(result: Result) {
        super.passResult(result)
        (result as? ConditionResult)?.let {
            this.rect = it.rect
        }
    }
}