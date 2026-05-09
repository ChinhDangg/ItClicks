package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.action.ActionInfo

data class KeyInfo(
    val keyCode: Int,
    val delay: Int,
    override val executor: KeyAction,
    override var result: Result?
) : ActionInfo<KeyInfo> {

    override fun getSelf(): KeyInfo = this
}