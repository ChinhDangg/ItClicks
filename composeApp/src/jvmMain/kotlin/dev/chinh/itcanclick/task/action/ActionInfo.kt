package dev.chinh.itcanclick.task.action

import dev.chinh.itcanclick.task.TaskInfo

abstract class ActionInfo<A : ActionInfo<A>>(
    executor: Action<A>
) : TaskInfo<A>(executor) {
}