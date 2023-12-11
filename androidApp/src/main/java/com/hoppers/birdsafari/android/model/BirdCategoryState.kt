package com.hoppers.birdsafari.android.model

import com.hoppers.birdsafari.model.BirdImage

data class BirdCategoryState(
    val images: List<BirdImage> = emptyList(),
    val selectedCategory: String? = null
) {
    val categories = images.map { it.category }.toSet()
    val selectedImages = images.filter {
        it.category == (selectedCategory ?: categories.first())
    }
}

sealed interface UIState{
    data object  Loading: UIState
    data class  UI(val birdCategoryState: BirdCategoryState): UIState

}