package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import dev.chinh.itcanclick.task.Task
import dev.chinh.itcanclick.task.TaskInfo

abstract class TaskWrapper<W : TaskWrapperInfo<W>> : Task<W> {

    abstract var tasksToRun: List<TaskInfo<*>>

    override fun execute(taskInfo: W): Result {
        return perform(taskInfo)
    }

    abstract fun perform(taskInfo: W) : Result

    fun runTasks() : Result {
        for (taskInfo in tasksToRun) {
            val result = taskInfo.selfExecute()
            if (result.result != ResultStatus.PASS) {
                return result
            }
        }
        return Result(ResultStatus.PASS, "All tasks passed")
    }
}