package dev.chinh.itcanclick.task.taskwrapper

data class NormalWrapperInfo(
    val taskExecutor: NormalWrapper,
): TaskWrapperInfo<NormalWrapperInfo>(taskExecutor) {

    override fun getSelf(): NormalWrapperInfo = this
}