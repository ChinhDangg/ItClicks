package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus

class LoopTask : TaskWrapper<LoopTaskInfo>() {

    override suspend fun execute(taskInfo: LoopTaskInfo): Result {
        return runLoop(taskInfo)
    }

    suspend fun runLoop(taskInfo: LoopTaskInfo) : Result {
        (0 until taskInfo.numLoops).forEach { _ ->
            val result = runTasks(taskInfo)
            if (result.result == ResultStatus.FAIL)
                return result
        }
        return Result(ResultStatus.PASS, "All tasks passed in loop")
    }

}