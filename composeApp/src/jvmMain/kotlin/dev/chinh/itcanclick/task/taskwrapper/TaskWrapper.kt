package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import dev.chinh.itcanclick.task.Task
import dev.chinh.itcanclick.task.TaskInfo

abstract class TaskWrapper<W : TaskWrapperInfo<W>> : Task<W> {

    override suspend fun execute(taskInfo: W): Result {
        return perform(taskInfo)
    }

    abstract suspend fun perform(taskInfo: W) : Result

    suspend fun runTasks(taskInfoList: List<TaskInfo<W>>) : Result {
        for (i in taskInfoList.indices) {
            val taskInfo = taskInfoList[i]
            val result = taskInfo.selfExecute()
            when (result.result) {
                ResultStatus.FAIL -> return result
                ResultStatus.SKIPPABLE -> return result
                ResultStatus.PASS -> continue
                ResultStatus.PASS_RESULT -> {
                    val nextTask = taskInfoList.getOrNull(i + 1)
                    nextTask?.passResult(result)
                }
            }
        }
        return Result(ResultStatus.PASS, "All tasks passed")
    }
}