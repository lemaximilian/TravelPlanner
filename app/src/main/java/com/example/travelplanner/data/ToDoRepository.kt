package com.example.travelplanner.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.travelplanner.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepository(private val todoDao: TodoDao) {

    fun getTodoItems(): LiveData<List<Todo>> = liveData {
        val items = withContext(Dispatchers.IO) {
            todoDao.getAll()
        }
        emit(items)
    }

    suspend fun addTodoItem(title: String) {
        withContext(Dispatchers.IO) {
            val todo = Todo(
                id = 0, // auto-generated id
                title = title,
                checked = false
            )
            todoDao.insertAll(todo)
        }
    }

    suspend fun updateTodoItem(todo: Todo) {
        withContext(Dispatchers.IO) {
            todoDao.update(todo)
        }
    }

    suspend fun deleteTodoItem(todo: Todo) {
        withContext(Dispatchers.IO) {
            todoDao.delete(todo)
        }
    }
}