package dev.chinh.itcanclick.task

interface TaskInfo<T : TaskInfo<T>> {
    val executor: Task<T>
    var result: Result?

    fun passResult(result: Result) {
        this.result = result
    }

    fun getSelf(): T

    suspend fun selfExecute() : Result {
        return executor.execute(getSelf())
    }
}