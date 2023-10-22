package com.hoppers.birdsafari.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoppers.birdsafari.android.model.BirdsUiState
import com.hoppers.birdsafari.repositories.BirdRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BirdViewModel(private val repository: BirdRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(BirdsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateImages()
    }

    override fun onCleared() {
        repository.close()
    }

    fun selectCategory(category: String) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
    }

    private fun updateImages() {
        viewModelScope.launch {
            val images = repository.getImages()
            _uiState.update {
                it.copy(images = images)
            }
        }
    }
}