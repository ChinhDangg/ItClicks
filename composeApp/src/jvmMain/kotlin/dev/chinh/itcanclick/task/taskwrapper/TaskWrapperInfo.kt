package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskInfo
import dev.chinh.itcanclick.task.TaskType

abstract class TaskWrapperInfo<T : TaskWrapperInfo<T>>(
    taskType: TaskType,
    executor: TaskWrapper<T>,
    val tasksToRun: List<TaskInfo<T>> = emptyList(),
) : TaskInfo<T>(taskType, executor) {

    override fun passResult(result: Result) {
        for (task in tasksToRun) {
            task.passResult(result)
        }
    }
}