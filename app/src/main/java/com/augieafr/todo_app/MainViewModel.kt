package com.augieafr.todo_app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augieafr.todo_app.data.repository.TodoRepository
import com.augieafr.todo_app.todolist.ListTodoScreenEvent
import com.augieafr.todo_app.ui.model.TodoUiModel
import com.augieafr.todo_app.utils.dueDateToDeadline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    // first argument is to show/hide the dialog, second argument is to determine dialog type (edit/add)
    var isShowAddEditTodo by mutableStateOf(Pair(false, false))

    val todoList = repository.getTodos()

    fun todoEventHandler(todo: TodoUiModel?, todoEvent: ListTodoScreenEvent) =
        viewModelScope.launch {
            when (todoEvent) {
                ListTodoScreenEvent.Delete -> {
                    todo?.let { repository.deleteTodo(it) }
                }

                is ListTodoScreenEvent.Done -> {
                    todo?.let { repository.addEditTodo(todo.copy(isDone = todoEvent.isDone)) }
                }

                ListTodoScreenEvent.Add -> {
                    // TODO : change implementation with goto add screen
                    isShowAddEditTodo = Pair(true, false)
                }

                ListTodoScreenEvent.Edit -> {
                    // TODO : change implementation with goto detail screen
                    isShowAddEditTodo = Pair(true, true)
                }

                is ListTodoScreenEvent.SaveTodo -> {
                    val newTodo = todo?.copy(
                        title = todoEvent.title,
                        description = todoEvent.description,
                        dueDate = todoEvent.dueDate,
                        deadLine = dueDateToDeadline(todoEvent.dueDate)
                    ) ?: run {
                        TodoUiModel(
                            id = 0,
                            title = todoEvent.title,
                            description = todoEvent.description,
                            dueDate = todoEvent.dueDate,
                            deadLine = dueDateToDeadline(todoEvent.dueDate),
                            isDone = false
                        )
                    }
                    repository.addEditTodo(
                        newTodo
                    )
                }
            }
        }

}