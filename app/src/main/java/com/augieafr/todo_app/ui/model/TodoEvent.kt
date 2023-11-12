package com.augieafr.todo_app.ui.model

sealed class TodoEvent {
    data object Delete : TodoEvent()
    data object Edit : TodoEvent()
    class Done(val isDone: Boolean) : TodoEvent()
    data object Add : TodoEvent()
    class SaveTodo(val title: String, val description: String, val dueDate: String) :
        TodoEvent()
}