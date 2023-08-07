package com.augieafr.todo_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.augieafr.todo_app.data.dao.TodoDao
import com.augieafr.todo_app.data.entity.TodoEntity

@Database(version = 1, entities = [TodoEntity::class])
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}