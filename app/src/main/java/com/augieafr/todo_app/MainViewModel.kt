package com.augieafr.todo_app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.augieafr.todo_app.ui.component.ToDoEvent
import com.augieafr.todo_app.ui.model.TodoUiModel
import com.augieafr.todo_app.utils.dueDateToDeadline
import kotlin.random.Random

class MainViewModel : ViewModel() {

    val listTodo = mutableStateListOf<TodoUiModel>()
    var isShowAddEditTodo: MutableState<ToDoEvent?> = mutableStateOf(null)

    init {
        listTodo.addAll(TodoUiModel.generateDummyTodoList())
    }

    // later change implementation with repository
    fun todoEventHandler(index: Int, toDoEvent: ToDoEvent) {
        when (toDoEvent) {
            ToDoEvent.Delete -> {
                listTodo.removeAt(index)
            }

            is ToDoEvent.Edit -> {
                isShowAddEditTodo.value = ToDoEvent.Edit(toDoEvent.todoUiModel)
            }

            is ToDoEvent.Done -> {
                listTodo[index] = listTodo[index].copy(isDone = toDoEvent.isDone)
            }

            is ToDoEvent.Add -> {
                isShowAddEditTodo.value = ToDoEvent.Add
            }

            is ToDoEvent.SaveTodo -> {
                with(toDoEvent) {
                    if (id != null) {
                        val selectedTodo = listTodo.singleOrNull {
                            it.id == id
                        } ?: return
                        val selectedTodoIndex = listTodo.indexOf(selectedTodo)
                        listTodo[selectedTodoIndex] = selectedTodo.copy(
                            id = id,
                            title = title,
                            description = description,
                            dueDate = dueDate,
                            deadLine = dueDateToDeadline(dueDate)
                        )
                    } else {
                        val todoModel = TodoUiModel(
                            id = Random.nextInt(255),
                            title = title,
                            dueDate = dueDate,
                            description = description,
                            deadLine = dueDateToDeadline(dueDate),
                            isDone = false
                        )
                        listTodo.add(index, todoModel)
                    }
                }
            }
        }
    }

}