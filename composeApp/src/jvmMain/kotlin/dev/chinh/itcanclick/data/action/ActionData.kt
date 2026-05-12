package dev.chinh.itcanclick.data.action

import dev.chinh.itcanclick.data.TaskData
import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.action.ActionInfo

interface ActionData<A : ActionInfo<A>> : TaskData<A> {

    override fun getTaskInfo(): TaskInfo<A> {
        return getMinimalTaskInfo()
    }
}