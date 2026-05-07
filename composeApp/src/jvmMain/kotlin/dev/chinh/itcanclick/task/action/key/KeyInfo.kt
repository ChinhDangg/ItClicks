package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.task.action.ActionInfo

data class KeyInfo(
    val keyCode: Int,
    val delay: Int = 0,
    val taskExecutor: KeyAction
) : ActionInfo<KeyInfo>(taskExecutor) {

    override fun getSelf(): KeyInfo = this
}