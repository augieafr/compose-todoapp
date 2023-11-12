package com.augieafr.todo_app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.augieafr.todo_app.detail_todo.DetailTodoScreen
import com.augieafr.todo_app.home.HomeScreen
import com.augieafr.todo_app.ui.navigation.Screen

@Composable
fun TodoApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                modifier,
                navigateToProfile = {},
                navigateToAddTodo = {},
                navigateToDetail = {
                    navController.navigate(Screen.DetailTodo.createRoute(it))

                })
        }
        composable(
            route = Screen.DetailTodo.route,
            arguments = listOf(navArgument(Screen.TODO_ID_KEY) {
                type = NavType.IntType
            })
        ) {
            val todoId = it.arguments?.getInt(Screen.TODO_ID_KEY) ?: -1
            DetailTodoScreen(modifier = modifier, todoId = todoId, onNavigateUp = {
                navController.navigateUp()
            })
        }
    }
}