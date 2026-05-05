package dev.chinh.itcanclick.task

enum class ResultStatus {
    PASS,
    FAIL,
    SKIPPABLE, // skip the rest of the task
    PASS_RESULT, // pass the result of the task to the next task
    SKIP_PASS // skippable and pass the result
}