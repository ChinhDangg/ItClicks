package dev.chinh.itcanclick.task

abstract class TaskInfo<T : TaskInfo<T>>(
    val taskType: TaskType,
    val executor: Task<T>,
    var result: Result? = null
) {
    open fun passResult(result: Result) {
        this.result = result
    }

    abstract fun getSelf(): T

    fun selfExecute() : Result {
        return executor.execute(getSelf())
    }
}