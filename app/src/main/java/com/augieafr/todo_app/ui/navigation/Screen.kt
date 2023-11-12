package com.augieafr.todo_app.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object AddTodo : Screen("add")
    data object DetailTodo : Screen("home/{$TODO_ID_KEY}") {
        fun createRoute(todoId: Int) = "home/$todoId"
    }

    companion object {
        const val TODO_ID_KEY = "todoId"
    }
}