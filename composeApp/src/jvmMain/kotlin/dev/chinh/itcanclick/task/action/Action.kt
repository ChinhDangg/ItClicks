package dev.chinh.itcanclick.task.action

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.Task

interface Action<A : ActionInfo<A>> : Task<A> {

    override fun execute(taskInfo: A) : Result {
        return perform(taskInfo)
    }

    fun perform(actionInfo: A) : Result
}
