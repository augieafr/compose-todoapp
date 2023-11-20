package com.augieafr.todo_app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augieafr.todo_app.data.repository.TodoRepository
import com.augieafr.todo_app.ui.model.GroupBy
import com.augieafr.todo_app.ui.model.TodoUiModel
import com.augieafr.todo_app.utils.dueDateToDeadline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String>
        get() = _searchQuery

    private val _todoGroupBy = MutableStateFlow(GroupBy.IS_DONE)
    val todoGroupBy: StateFlow<GroupBy>
        get() = _todoGroupBy

    fun setQuery(query: String) {
        _searchQuery.value = query
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val todoList: Flow<Map<String, List<TodoUiModel>>> =
        merge(searchQuery, todoGroupBy).flatMapLatest {
            repository.getTodos(todoGroupBy.value, searchQuery.value)
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

    fun changeGroupBy() {
        if (_todoGroupBy.value == GroupBy.IS_DONE) {
            _todoGroupBy.value = GroupBy.DEADLINE
        } else {
            _todoGroupBy.value = GroupBy.IS_DONE
        }
    }

    fun addTodo(title: String, description: String, dueDate: String) = viewModelScope.launch {
        repository.addEditTodo(
            TodoUiModel(
                id = 0,
                title = title,
                description = description,
                deadLine = dueDateToDeadline(sourcePattern = "MMM dd, yyyy", dueDate = dueDate),
                isDone = false
            )
        )
    }
}