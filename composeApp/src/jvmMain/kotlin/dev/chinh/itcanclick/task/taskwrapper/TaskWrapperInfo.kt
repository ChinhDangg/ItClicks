package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.TaskInfo

abstract class TaskWrapperInfo<T : TaskWrapperInfo<T>>(
    executor: TaskWrapper<T>,
    val tasksToRun: List<TaskInfo<*>> = emptyList()
) : TaskInfo<T>(executor) {
}