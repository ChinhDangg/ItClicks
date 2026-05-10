package dev.chinh.itcanclick.task.wrapper

import dev.chinh.itcanclick.task.Result

class NormalWrapper : TaskWrapper<NormalWrapperInfo>() {

    override suspend fun execute(taskInfo: NormalWrapperInfo): Result {
        return runNormal(taskInfo)
    }

    suspend fun runNormal(taskInfo: NormalWrapperInfo): Result {
        return runTasks(taskInfo)
    }
}