package com.app.sduiproductdetails.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.app.sduiproductdetails.model.ProductUIState
import com.app.sduiproductdetails.repository.ProductRepository

class ProductScrViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProductRepository()
    private val _uiState = MutableStateFlow(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState
    private val context: Context
        get() = getApplication<Application>().applicationContext
    init {
        loadProductDetails()
    }

    private fun loadProductDetails() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val data = repository.fetchProductUI(context = context)
                _uiState.value = ProductUIState(
                    isLoading = false,
                    page = data.page,
                    theme = data.theme
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}

