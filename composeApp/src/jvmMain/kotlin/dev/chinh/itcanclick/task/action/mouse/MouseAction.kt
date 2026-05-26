package dev.chinh.itcanclick.task.action.mouse

import dev.chinh.itcanclick.task.action.Action
import java.awt.Robot

interface MouseAction<M : MouseInfo<M>> : Action<M>