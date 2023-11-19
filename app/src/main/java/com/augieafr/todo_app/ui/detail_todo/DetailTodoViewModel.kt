package com.augieafr.todo_app.ui.detail_todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augieafr.todo_app.data.repository.TodoRepository
import com.augieafr.todo_app.ui.model.TodoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTodoViewModel @Inject constructor(private val todoRepository: TodoRepository) :
    ViewModel() {
    private val _todo = MutableSharedFlow<TodoUiModel>()
    val todo: SharedFlow<TodoUiModel>
        get() = _todo

    fun getTodo(id: Int) = viewModelScope.launch {
        _todo.emit(todoRepository.getTodoById(id))
    }

    fun saveTodo(oldTodo: TodoUiModel, title: String, description: String, dueDate: String) =
        viewModelScope.launch {
            val newTodo = oldTodo.copy(title = title, description = description, dueDate = dueDate)
            todoRepository.addEditTodo(
                newTodo
            )
            // update TodoUiModel with the new one so the undo feature can work properly
            _todo.emit(newTodo)
        }
}
