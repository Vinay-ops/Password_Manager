package com.example.passwordmanager.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).passwordDao()
    
    val allPasswords: Flow<List<PasswordEntity>> = dao.getAllPasswords()

    fun getPasswordsByCategory(category: String): Flow<List<PasswordEntity>> {
        return dao.getPasswordsByCategory(category)
    }

    fun addPassword(password: PasswordEntity) {
        viewModelScope.launch {
            dao.insertPassword(password)
        }
    }

    fun deletePassword(password: PasswordEntity) {
        viewModelScope.launch {
            dao.deletePassword(password)
        }
    }
}
