package com.moguishio.moguishio.model.cats

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class CatViewModel : ViewModel() {
    private val repository = CatRepository()

    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> = _cats

    fun fetchCreditCards() {
        viewModelScope.launch {
            try {
                val cats = repository.getCats()
                _cats.value = cats
            } catch (e: Exception) {
                Log.e("Error", "No sé cuál es el error xD")
            }
        }
    }
}