package com.example.travelplanner.data

import androidx.lifecycle.LiveData
import com.example.travelplanner.model.Expenses
import java.util.*

class ExpensesRepository(private val expensesDao: ExpensesDao) {

    val readAllData: LiveData<List<Expenses>> = expensesDao.getAll()

    fun getExpensesByID(id: UUID): LiveData<Expenses> = expensesDao.getExpensesByID(id)

    suspend fun addExpenses(expenses: Expenses) {
        expensesDao.insert(expenses)
    }

    suspend fun updateExpenses(expenses: Expenses) {
        expensesDao.update(expenses)
    }

    suspend fun deleteExpenses(expenses: Expenses) {
        expensesDao.delete(expenses)
    }

    suspend fun deleteAllExpenses() {
        expensesDao.deleteAll()
    }
}