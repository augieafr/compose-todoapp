package com.augieafr.todo_app.data.repository

import com.augieafr.todo_app.data.dao.TodoDao
import com.augieafr.todo_app.data.mapper.toTodoEntity
import com.augieafr.todo_app.data.mapper.toTodoUiModel
import com.augieafr.todo_app.ui.model.GroupBy
import com.augieafr.todo_app.ui.model.ToDoDeadline
import com.augieafr.todo_app.ui.model.TodoUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao) {
    fun getTodos(groupBy: GroupBy) = todoDao.getTodos().map {
        val mapTodo = mutableMapOf<String, List<TodoUiModel>>()
        it.forEach { todoEntity ->
            val todoUiModel = todoEntity.toTodoUiModel()
            when (groupBy) {
                GroupBy.DEADLINE -> {
                    if (todoUiModel.isDone) {
                        mapTodo["Done"] = mapTodo["Done"]?.plus(todoUiModel) ?: listOf(todoUiModel)
                        return@forEach
                    }
                    when (todoUiModel.deadLine) {
                        is ToDoDeadline.FAR -> mapTodo["Far"] =
                            mapTodo["Far"]?.plus(todoUiModel) ?: listOf(todoUiModel)

                        is ToDoDeadline.MID -> mapTodo["Mid"] =
                            mapTodo["Mid"]?.plus(todoUiModel) ?: listOf(todoUiModel)

                        is ToDoDeadline.NEAR -> mapTodo["Near"] =
                            mapTodo["Near"]?.plus(todoUiModel) ?: listOf(todoUiModel)
                    }
                }

                GroupBy.IS_DONE -> {
                    if (todoUiModel.isDone) mapTodo["Done"] =
                        mapTodo["Done"]?.plus(todoUiModel) ?: listOf(todoUiModel)
                    else mapTodo["To do"] =
                        mapTodo["To do"]?.plus(todoUiModel) ?: listOf(todoUiModel)
                }
            }
        }
        mapTodo
    }.flowOn(Dispatchers.IO)

    suspend fun addEditTodo(todoUiModel: TodoUiModel) =
        todoDao.addUpdateTodo(todoUiModel.toTodoEntity())


    suspend fun deleteTodo(todoUiModel: TodoUiModel) =
        todoDao.deleteTodo(todoUiModel.toTodoEntity())
}