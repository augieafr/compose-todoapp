package com.augieafr.todo_app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augieafr.todo_app.data.repository.TodoRepository
import com.augieafr.todo_app.ui.component.ToDoEvent
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
    var isShowAddEditTodo: MutableState<ToDoEvent?> = mutableStateOf(null)

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
    fun todoEventHandler(index: Int, toDoEvent: ToDoEvent) = viewModelScope.launch {
        when (toDoEvent) {
            ToDoEvent.Delete -> {
                repository.deleteTodo(listTodo[index])
                listTodo.removeAt(index)
            }

            is ToDoEvent.Edit -> {
                isShowAddEditTodo.value = ToDoEvent.Edit(toDoEvent.todoUiModel)
            }

            is ToDoEvent.Done -> {
                val updatedTodo = listTodo[index].copy(isDone = toDoEvent.isDone)
                listTodo[index] = updatedTodo
                repository.addEditTodo(updatedTodo)
            }

            is ToDoEvent.Add -> {
                isShowAddEditTodo.value = ToDoEvent.Add
            }

            is ToDoEvent.SaveTodo -> {
                with(toDoEvent) {

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
        }
    }

}