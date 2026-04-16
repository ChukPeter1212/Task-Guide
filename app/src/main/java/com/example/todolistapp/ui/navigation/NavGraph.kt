package com.example.todolistapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolistapp.ui.screens.AddTaskScreen
import com.example.todolistapp.ui.screens.CompletedTasksScreen
import com.example.todolistapp.ui.screens.EditTaskScreen
import com.example.todolistapp.ui.screens.HomeScreen
import com.example.todolistapp.ui.viewmodel.TaskViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddTask : Screen("add_task")
    object EditTask : Screen("edit_task/{taskId}") {
        fun createRoute(taskId: Int) = "edit_task/$taskId"
    }
    object CompletedTasks : Screen("completed_tasks")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val viewModel: TaskViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.AddTask.route) {
            AddTaskScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = Screen.EditTask.route,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: return@composable
            EditTaskScreen(navController = navController, viewModel = viewModel, taskId = taskId)
        }

        composable(Screen.CompletedTasks.route) {
            CompletedTasksScreen(navController = navController, viewModel = viewModel)
        }
    }
}