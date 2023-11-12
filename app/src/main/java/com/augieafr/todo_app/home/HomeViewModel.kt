package com.augieafr.todo_app.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augieafr.todo_app.data.repository.TodoRepository
import com.augieafr.todo_app.ui.model.TodoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    val todoList = repository.getTodos()
    fun setTodoIsDone(todo: TodoUiModel) = viewModelScope.launch {
        repository.addEditTodo(
            todo
        )
    }

    fun deleteTodo(todo: TodoUiModel) = viewModelScope.launch {
        repository.deleteTodo(
            todo
        )
    }
}