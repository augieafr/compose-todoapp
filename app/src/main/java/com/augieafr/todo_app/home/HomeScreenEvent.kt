package com.augieafr.todo_app.home

sealed class HomeScreenEvent {
    data object Delete : HomeScreenEvent()
    data object Detail : HomeScreenEvent()
    class Done(val isDone: Boolean) : HomeScreenEvent()
}