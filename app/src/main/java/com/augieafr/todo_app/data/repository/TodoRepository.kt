package com.augieafr.todo_app.data.repository

import com.augieafr.todo_app.data.dao.TodoDao
import com.augieafr.todo_app.data.mapper.toTodoEntity
import com.augieafr.todo_app.data.mapper.toTodoUiModel
import com.augieafr.todo_app.ui.model.TodoUiModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao) {
    suspend fun getTodos() =
        flow<List<TodoUiModel>> {
            val todoList = mutableListOf<TodoUiModel>()
            todoDao.getTodos().forEach {
                val todo = it.toTodoUiModel()
                todoList.add(todo)
            }
            emit(todoList)
        }

    suspend fun addEditTodo(todoUiModel: TodoUiModel) =
        todoDao.addUpdateTodo(todoUiModel.toTodoEntity())


    suspend fun deleteTodo(todoUiModel: TodoUiModel) =
        todoDao.deleteTodo(todoUiModel.toTodoEntity())
}