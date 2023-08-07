package com.augieafr.todo_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.augieafr.todo_app.data.entity.TodoEntity

@Dao
interface TodoDao {
    @Query("SELECT * from todo")
    suspend fun getTodos(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUpdateTodo(todoEntity: TodoEntity): Long

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)
}