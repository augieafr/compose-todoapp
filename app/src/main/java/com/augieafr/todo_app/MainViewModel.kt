package com.augieafr.todo_app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augieafr.todo_app.data.repository.TodoRepository
import com.augieafr.todo_app.ui.model.TodoEvent
import com.augieafr.todo_app.ui.model.TodoUiModel
import com.augieafr.todo_app.utils.dueDateToDeadline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    val listTodo = mutableStateListOf<TodoUiModel>()

    // first argument is to show/hide the dialog, second argument is to determine dialog type (edit/add)
    var isShowAddEditTodo by mutableStateOf(Pair(false, false))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTodos().collectLatest {
                withContext(Dispatchers.Main) {
                    listTodo.addAll(it)
                }
            }
        }
    }

    // later change implementation with repository
    fun todoEventHandler(index: Int, id: Int?, todoEvent: TodoEvent) = viewModelScope.launch {
        when (todoEvent) {
            TodoEvent.Delete -> {
                repository.deleteTodo(listTodo[index])
                listTodo.removeAt(index)
            }

            is TodoEvent.Done -> {
                val updatedTodo = listTodo[index].copy(isDone = todoEvent.isDone)
                listTodo[index] = updatedTodo
                repository.addEditTodo(updatedTodo)
            }

            is TodoEvent.SaveTodo -> {
                with(todoEvent) {
                    id?.let { id ->
                        val todo = listTodo.single {
                            it.id == id
                        }

                        val editedTodoIndex = listTodo.indexOf(todo)
                        repository.addEditTodo(listTodo[editedTodoIndex])
                        listTodo[editedTodoIndex] = listTodo[editedTodoIndex].copy(
                            title = title,
                            description = description,
                            dueDate = dueDate,
                            deadLine = dueDateToDeadline(dueDate)
                        )
                    } ?: run {
                        val todo = TodoUiModel(
                            0, title = title,
                            description = description,
                            dueDate = dueDate,
                            deadLine = dueDateToDeadline(dueDate),
                            isDone = false
                        )
                        val newId = repository.addEditTodo(todo)
                        listTodo.add(0, todo.copy(id = newId.toInt()))
                    }
                }
            }

            TodoEvent.Add -> {
                // TODO : change implementation with goto add screen
                isShowAddEditTodo = Pair(true, false)
            }

            TodoEvent.Edit -> {
                // TODO : change implementation with goto detail screen
                isShowAddEditTodo = Pair(true, true)
            }
        }
    }

}