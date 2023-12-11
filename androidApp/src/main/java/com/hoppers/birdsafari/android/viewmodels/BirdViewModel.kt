package com.hoppers.birdsafari.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoppers.birdsafari.android.model.UIState
import com.hoppers.birdsafari.android.model.BirdCategoryState
import com.hoppers.birdsafari.repositories.BirdRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BirdViewModel(private val repository: BirdRepository) : ViewModel() {
    private val _categoryState = MutableStateFlow(BirdCategoryState())
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val categoryState = _categoryState.asStateFlow()
    val birdState = _uiState.asStateFlow()

    init {
        getImages()
    }

    override fun onCleared() {
        repository.close()
    }

    fun selectCategory(category: String) {
        _categoryState.update {
            it.copy(selectedCategory = category)
        }
    }

    private fun getImages() {
        viewModelScope.launch(Dispatchers.Main) {
            flow<UIState> {
                val images = repository.getImages()
                emit(
                    UIState.UI(
                        run {
                            _categoryState.update { it.copy(images = images) }
                            _categoryState.value
                        })
                )
            }.catch {
                //emit(BirdState.Error(it.message ?: "Error -----"))
            }.flowOn(Dispatchers.IO).collect { state ->
                _uiState.value = state
            }
        }
    }
}