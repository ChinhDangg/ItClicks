package dev.chinh.itcanclick.task.wrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.ResultStatus
import dev.chinh.itcanclick.task.TaskRegistry
import org.springframework.stereotype.Component

@Component
class LoopTask : TaskWrapper<LoopTaskInfo> {

    private val taskRegistry: TaskRegistry

    constructor(taskRegistry: TaskRegistry) {
        this.taskRegistry = taskRegistry
    }

    override suspend fun execute(taskInfo: LoopTaskInfo): Result {
        return runLoop(taskInfo)
    }

    suspend fun runLoop(taskInfo: LoopTaskInfo) : Result {
        var result = Result(ResultStatus.PASS, "All tasks: ${taskInfo.tasksToRun.size} passed in loop")
        (0 until taskInfo.numLoops).forEach { _ ->
            result = runTasks(taskRegistry, taskInfo)
            if (result.result == ResultStatus.FAIL)
                return result
        }
        return result
    }

}