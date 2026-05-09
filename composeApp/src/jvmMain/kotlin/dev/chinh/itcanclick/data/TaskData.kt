package dev.chinh.itcanclick.data

import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.type.TaskType

interface TaskData<T : TaskInfo<T>>
{
    val taskType: TaskType

    fun getTaskInfo(): TaskInfo<T>
}