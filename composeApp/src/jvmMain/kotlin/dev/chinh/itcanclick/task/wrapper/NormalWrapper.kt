package dev.chinh.itcanclick.task.wrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskRegistry
import org.springframework.stereotype.Component

@Component
class NormalWrapper : TaskWrapper<NormalWrapperInfo> {

    private val taskRegistry: TaskRegistry

    constructor(taskRegistry: TaskRegistry) {
        this.taskRegistry = taskRegistry
    }

    override suspend fun execute(taskInfo: NormalWrapperInfo): Result {
        return runNormal(taskInfo)
    }

    suspend fun runNormal(taskInfo: NormalWrapperInfo): Result {
        return runTasks(taskRegistry, taskInfo)
    }
}