package com.augieafr.todo_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.augieafr.todo_app.data.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo WHERE title LIKE '%' || :query || '%' ORDER BY isDone ASC, dueDate ASC")
    fun getTodos(query: String): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo ORDER BY isDone ASC, dueDate ASC")
    fun getTodos(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUpdateTodo(todoEntity: TodoEntity): Long

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)
}