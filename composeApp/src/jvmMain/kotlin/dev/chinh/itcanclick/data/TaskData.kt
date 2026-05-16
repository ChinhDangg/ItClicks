package dev.chinh.itcanclick.data

import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.type.TaskType

interface TaskData<T : TaskInfo<T>>
{
    val id: String
    val name: String
    val taskType: TaskType

    fun getTaskInfo(): TaskInfo<T>

    fun getMinimalTaskInfo(): TaskInfo<T>
}