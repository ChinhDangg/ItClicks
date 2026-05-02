package dev.chinh.itcanclick.task.taskwrapper

import dev.chinh.itcanclick.task.TaskType

data class NormalWrapperInfo(
    val taskExecutor: TaskWrapper<NormalWrapperInfo>,
): TaskWrapperInfo<NormalWrapperInfo>(TaskType.WRAPPER, taskExecutor) {

    override fun getSelf(): NormalWrapperInfo = this
}