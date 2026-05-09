package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.TaskInfo

interface TaskWrapperInfo<T : TaskWrapperInfo<T>> : TaskInfo<T> {
    val tasksToRun: List<TaskInfo<*>>
}