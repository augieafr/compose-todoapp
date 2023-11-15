package com.augieafr.todo_app.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augieafr.todo_app.data.repository.TodoRepository
import com.augieafr.todo_app.ui.component.dropdown_menu.GroupBy
import com.augieafr.todo_app.ui.model.TodoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    var todoGroupBy = MutableStateFlow(GroupBy.IS_DONE)

    @OptIn(ExperimentalCoroutinesApi::class)
    val todoList = todoGroupBy.flatMapLatest { groupBy ->
        repository.getTodos(groupBy)
    }

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