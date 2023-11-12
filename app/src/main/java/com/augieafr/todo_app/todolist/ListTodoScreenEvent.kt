package com.augieafr.todo_app.todolist

sealed class ListTodoScreenEvent {
    data object Delete : ListTodoScreenEvent()
    data object Edit : ListTodoScreenEvent()
    class Done(val isDone: Boolean) : ListTodoScreenEvent()
    data object Add : ListTodoScreenEvent()
    class SaveTodo(val title: String, val description: String, val dueDate: String) :
        ListTodoScreenEvent()
}