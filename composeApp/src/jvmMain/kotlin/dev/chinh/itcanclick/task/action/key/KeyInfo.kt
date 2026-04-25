package dev.chinh.itcanclick.task.action.key

import dev.chinh.itcanclick.task.action.ActionInfo

data class KeyInfo(
    var keyCode: Int,
    var delay: Int = 0,
) : ActionInfo