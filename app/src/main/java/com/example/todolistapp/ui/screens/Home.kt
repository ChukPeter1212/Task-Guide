package com.example.todolistapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolistapp.ui.navigation.Screen
import com.example.todolistapp.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: TaskViewModel) {
    val tasks by viewModel.activeTasks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TaskGuide") },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.CompletedTasks.route) }) {
                        Icon(Icons.Default.CheckCircle, contentDescription = "Completed Tasks")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddTask.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No tasks yet. Tap + to add one!")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks) { task ->
                    Card(
                        onClick = { navController.navigate(Screen.EditTask.createRoute(task.id)) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = task.isCompleted,
                                onCheckedChange = { viewModel.toggleComplete(task) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = task.title, style = MaterialTheme.typography.titleMedium)
                                if (task.dueDate.isNotEmpty()) {
                                    Text(text = task.dueDate, style = MaterialTheme.typography.bodySmall)
                                }
                                AssistChip(
                                    onClick = {},
                                    label = { Text(task.category) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}