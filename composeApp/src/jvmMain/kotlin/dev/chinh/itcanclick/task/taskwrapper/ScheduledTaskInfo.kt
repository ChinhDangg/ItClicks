package dev.chinh.itcanclick.task.taskwrapper

data class ScheduledTaskInfo(
    val delay: Long,
    val taskExecutor: ScheduledTask,
) : TaskWrapperInfo<ScheduledTaskInfo>(taskExecutor) {

    override fun getSelf(): ScheduledTaskInfo = this
}
