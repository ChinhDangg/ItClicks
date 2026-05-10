package dev.chinh.itcanclick.task.wrapper

import dev.chinh.itcanclick.task.TaskInfo

interface TaskWrapperInfo<T : TaskWrapperInfo<T>> : TaskInfo<T> {
    val tasksToRun: List<TaskInfo<*>>
}