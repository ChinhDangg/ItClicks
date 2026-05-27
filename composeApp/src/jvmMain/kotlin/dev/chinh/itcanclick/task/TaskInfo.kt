package dev.chinh.itcanclick.task

import dev.chinh.itcanclick.data.TaskData
import dev.chinh.itcanclick.task.type.TaskType

interface TaskInfo<T : TaskInfo<T>> {
    val id: String
    val name: String
    val taskType: TaskType
    var result: Result?

    fun passResult(result: Result) {
        this.result = result
    }

    fun getSelf(): T

    suspend fun selfExecute(taskRegistry: TaskRegistry) : Result {
        return taskRegistry.getTask<T>(taskType).execute(getSelf())
    }

    fun getTaskData(): TaskData<T>
}