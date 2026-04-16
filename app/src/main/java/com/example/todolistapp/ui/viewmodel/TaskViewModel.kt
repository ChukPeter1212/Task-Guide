package com.example.todolistapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.Task
import com.example.todolistapp.data.TaskDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = TaskDatabase.getDatabase(application).taskDao()

    val activeTasks: StateFlow<List<Task>> = dao.getActiveTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val completedTasks: StateFlow<List<Task>> = dao.getCompletedTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTask(task: Task) = viewModelScope.launch {
        dao.insertTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        dao.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        dao.deleteTask(task)
    }

    fun toggleComplete(task: Task) = viewModelScope.launch {
        dao.updateTask(task.copy(isCompleted = !task.isCompleted))
    }
}