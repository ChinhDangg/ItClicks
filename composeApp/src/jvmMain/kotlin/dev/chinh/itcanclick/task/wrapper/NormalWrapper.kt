package dev.chinh.itcanclick.task.wrapper

import dev.chinh.itcanclick.task.Result
import dev.chinh.itcanclick.task.TaskRegistry
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component(NormalWrapper.BEAN_NAME)
class NormalWrapper(
    @Lazy override val taskRegistry: TaskRegistry
) : TaskWrapper<NormalWrapperInfo> {

    companion object { const val BEAN_NAME = "NORMAL" }

    override suspend fun execute(taskInfo: NormalWrapperInfo): Result {
        return runNormal(taskInfo)
    }

    suspend fun runNormal(taskInfo: NormalWrapperInfo): Result {
        return runTasks(taskInfo)
    }
}