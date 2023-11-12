package com.augieafr.todo_app.data.repository

import com.augieafr.todo_app.data.dao.TodoDao
import com.augieafr.todo_app.data.mapper.toTodoEntity
import com.augieafr.todo_app.data.mapper.toTodoUiModel
import com.augieafr.todo_app.ui.model.TodoUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao) {
    fun getTodos() = todoDao.getTodos().map {
        it.map { todoEntity -> todoEntity.toTodoUiModel() }
    }.flowOn(Dispatchers.IO)

    suspend fun addEditTodo(todoUiModel: TodoUiModel) =
        todoDao.addUpdateTodo(todoUiModel.toTodoEntity())


    suspend fun deleteTodo(todoUiModel: TodoUiModel) =
        todoDao.deleteTodo(todoUiModel.toTodoEntity())
}