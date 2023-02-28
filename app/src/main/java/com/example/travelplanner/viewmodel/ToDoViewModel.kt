package com.example.travelplanner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.travelplanner.data.TodoRepository
import com.example.travelplanner.data.TodoRoomDatabase
import com.example.travelplanner.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    private val Repository: TodoRepository = TodoRepository(TodoRoomDatabase.getDatabase(application).todoDao())
    val todoItems: LiveData<List<Todo>> = Repository.getTodoItems()

    fun addTodoItem(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.addTodoItem(title)


        }
    }

    fun updateTodoItem(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.updateTodoItem(todo)

        }
    }

    fun deleteTodoItem(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.deleteTodoItem(todo)

        }
    }
}

