package dev.chinh.itcanclick.task.action

import dev.chinh.itcanclick.task.TaskInfo

interface ActionInfo<A : ActionInfo<A>> : TaskInfo<A>