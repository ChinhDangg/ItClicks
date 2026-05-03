package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import dev.chinh.itcanclick.task.Task
import dev.chinh.itcanclick.task.TaskInfo

abstract class TaskWrapper<W : TaskWrapperInfo<W>> : Task<W> {

    suspend fun runTasks(taskWrapperInfo: TaskWrapperInfo<W>) : Result {
        var wrapperResult = Result(ResultStatus.PASS, "All tasks passed")
        for (taskInfo in taskWrapperInfo.tasksToRun) {
            taskWrapperInfo.result?.let {
                taskInfo.passResult(it) // passing result from wrapper parent to inner
            }
            val result = taskInfo.selfExecute()
            when (result.result) {
                ResultStatus.FAIL -> return result
                ResultStatus.SKIPPABLE -> {
                    if (wrapperResult.result == ResultStatus.PASS)
                        return result
                    return wrapperResult
                }
                ResultStatus.PASS -> continue
                ResultStatus.PASS_RESULT -> {
                    wrapperResult = result // parent wrapper will pass result to next task if not failed
                    taskWrapperInfo.result = result // passing result from inner to wrapper parent
                }
            }
        }
        return wrapperResult
    }
}