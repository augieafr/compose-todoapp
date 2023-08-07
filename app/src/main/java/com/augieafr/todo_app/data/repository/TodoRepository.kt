package com.augieafr.todo_app.data.repository

import com.augieafr.todo_app.data.dao.TodoDao
import com.augieafr.todo_app.data.mapper.toTodoEntity
import com.augieafr.todo_app.data.mapper.toTodoUiModel
import com.augieafr.todo_app.ui.model.TodoUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao) {
    suspend fun getTodos() = flow<List<TodoUiModel>> {
        todoDao.getTodos().collectLatest {
            val listTodoUiModel = mutableListOf<TodoUiModel>()
            it.forEach { todoEntity ->
                listTodoUiModel.add(todoEntity.toTodoUiModel())
            }
            emit(listTodoUiModel)
        }
    }

    fun addEditTodo(todoUiModel: TodoUiModel) = todoDao.addUpdateTodo(todoUiModel.toTodoEntity())


    fun deleteTodo(todoUiModel: TodoUiModel) = todoDao.deleteTodo(todoUiModel.toTodoEntity())
}