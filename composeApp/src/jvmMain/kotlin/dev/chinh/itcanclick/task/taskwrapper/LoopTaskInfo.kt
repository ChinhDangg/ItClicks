package dev.chinh.itcanclick.task.taskwrapper

data class LoopTaskInfo(
    val numLoops: Int,
    val taskExecutor: LoopTask
) : TaskWrapperInfo<LoopTaskInfo>(taskExecutor) {

    override fun getSelf(): LoopTaskInfo = this
}
