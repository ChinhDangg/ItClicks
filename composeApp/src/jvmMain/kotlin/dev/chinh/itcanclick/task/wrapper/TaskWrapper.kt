package dev.chinh.itcanclick.task.wrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import dev.chinh.itcanclick.task.Task
import dev.chinh.itcanclick.task.TaskRegistry

interface TaskWrapper<W : TaskWrapperInfo<W>> : Task<W> {

    suspend fun runTasks(taskRegistry: TaskRegistry, taskWrapperInfo: TaskWrapperInfo<W>) : Result {
        var wrapperResult = Result(ResultStatus.PASS, "All tasks passed")
        for (taskInfo in taskWrapperInfo.tasksToRun) {
            taskWrapperInfo.result?.let {
                taskInfo.passResult(it) // passing result from wrapper parent to inner
            }
            val result = taskInfo.selfExecute(taskRegistry)
            when (result.result) {
                ResultStatus.FAIL -> return result
                ResultStatus.SKIP_PASS -> return result
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