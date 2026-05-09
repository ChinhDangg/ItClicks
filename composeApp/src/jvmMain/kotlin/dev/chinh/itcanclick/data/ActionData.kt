package dev.chinh.itcanclick.data

import dev.chinh.itcanclick.task.action.ActionInfo

interface ActionData<A : ActionInfo<A>> : TaskData<A>

