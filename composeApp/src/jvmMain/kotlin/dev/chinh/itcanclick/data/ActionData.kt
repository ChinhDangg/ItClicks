package dev.chinh.itcanclick.data

import dev.chinh.itcanclick.task.action.ActionInfo
import dev.chinh.itcanclick.task.type.TaskType

abstract class ActionData<A : ActionInfo<A>>(
    taskType: TaskType
) : TaskData<A>(taskType) {
}

