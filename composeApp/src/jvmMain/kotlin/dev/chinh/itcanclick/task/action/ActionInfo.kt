package dev.chinh.itcanclick.task.action

import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.TaskType

abstract class ActionInfo<A : ActionInfo<A>>(
    taskType: TaskType,
    executor: Action<A>
) : TaskInfo<A>(taskType, executor) {
}