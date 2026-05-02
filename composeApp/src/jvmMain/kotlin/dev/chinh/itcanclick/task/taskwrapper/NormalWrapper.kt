package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.Result

class NormalWrapper : TaskWrapper<NormalWrapperInfo>() {

    override suspend fun perform(taskInfo: NormalWrapperInfo): Result {
        return runNormal(taskInfo)
    }

    suspend fun runNormal(taskInfo: NormalWrapperInfo): Result {
        return runTasks(taskInfo.tasksToRun)
    }
}