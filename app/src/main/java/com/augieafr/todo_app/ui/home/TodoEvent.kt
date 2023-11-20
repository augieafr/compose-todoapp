package com.augieafr.todo_app.ui.home

sealed class TodoEvent {
    data object Delete : TodoEvent()
    data object Detail : TodoEvent()
    class Done(val isDone: Boolean) : TodoEvent()
}