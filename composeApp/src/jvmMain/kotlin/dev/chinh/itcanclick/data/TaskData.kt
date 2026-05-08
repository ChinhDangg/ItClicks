package dev.chinh.itcanclick.data

import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.type.TaskType

abstract class TaskData<T : TaskInfo<T>>(
    val taskType: TaskType
) {
    abstract fun getTaskData(): TaskData<T>

    abstract fun getTaskInfo(): TaskInfo<T>
}