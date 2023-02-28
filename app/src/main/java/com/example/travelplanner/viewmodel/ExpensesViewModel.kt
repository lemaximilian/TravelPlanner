package com.example.travelplanner.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.travelplanner.data.AppDatabase
import com.example.travelplanner.data.ExpensesRepository
import com.example.travelplanner.model.Expenses
import com.example.travelplanner.model.Traveler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ExpensesViewModel(app: Application): AndroidViewModel(app) {

    class Factory(private val app: Application): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExpensesViewModel(app) as T
        }
    }

    val context = app
    val readAllData: LiveData<List<Expenses>>
    private val expensesRepository: ExpensesRepository

    init {
        val expensesDao = AppDatabase.getInstance(app).ExpensesDao()
        expensesRepository = ExpensesRepository(expensesDao)
        readAllData = expensesRepository.readAllData
    }

    fun getExpensesByID(id: UUID) = expensesRepository.getExpensesByID(id)

    fun addExpenses(expenses: Expenses) {
        viewModelScope.launch(Dispatchers.IO) {
            expensesRepository.addExpenses(expenses)
        }
    }

    fun updateExpenses(expenses: Expenses) {
        viewModelScope.launch(Dispatchers.IO) {
            expensesRepository.updateExpenses(expenses)
        }
    }

    fun deleteExpenses(expenses: Expenses) {
        viewModelScope.launch(Dispatchers.IO) {
            expensesRepository.deleteExpenses(expenses)
        }
    }

    fun deleteAllExpenses() {
        viewModelScope.launch(Dispatchers.IO) {
            expensesRepository.deleteAllExpenses()
        }
    }
}