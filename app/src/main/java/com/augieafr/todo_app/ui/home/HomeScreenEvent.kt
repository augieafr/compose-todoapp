package com.augieafr.todo_app.ui.home

sealed class HomeScreenEvent {
    data object Delete : HomeScreenEvent()
    data object Detail : HomeScreenEvent()
    class Done(val isDone: Boolean) : HomeScreenEvent()
}