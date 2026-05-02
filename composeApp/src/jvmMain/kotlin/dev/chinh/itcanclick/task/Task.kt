package dev.chinh.itcanclick.task

interface Task<T : TaskInfo<T>> {

    suspend fun execute(taskInfo: T) : Result

    val taskClass: Class<out Task<*>>
        get() = this::class.java
}