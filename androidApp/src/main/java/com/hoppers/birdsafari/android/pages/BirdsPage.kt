package com.hoppers.birdsafari.android.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hoppers.birdsafari.android.model.BirdCategoryState
import com.hoppers.birdsafari.android.model.UIState
import com.hoppers.birdsafari.model.BirdImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BirdsPage(uiState: StateFlow<UIState>, categoryState: StateFlow<BirdCategoryState>, onSelectCategory: (category: String) -> Unit) {

    when (val result = uiState.collectAsState().value) {

        is UIState.Loading -> {}
        is UIState.UI -> {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    for (category in result.birdCategoryState.categories) {
                        Button(
                            onClick = { onSelectCategory(category) },
                            modifier = Modifier
                                .aspectRatio(1.0f)
                                .fillMaxSize()
                                .weight(1.0f),
                            elevation = ButtonDefaults.elevation(
                                defaultElevation = 0.dp,
                                focusedElevation = 0.dp
                            )
                        )
                        {
                            Text(category)
                        }
                    }
                }
                val selectedImages =categoryState.collectAsState().value.selectedImages
                AnimatedVisibility(selectedImages.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 5.dp),
                        content = {
                            items(selectedImages) {
                                BirdImageCell(it)
                            }
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun BirdImageCell(image: BirdImage) {
    AsyncImage(
        model = "https://sebastianaigner.github.io/demo-image-api/${image.path}",
        "${image.category} by ${image.author}",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.0f)
    )
}

@Preview
@Composable
fun BirdsPagePreview() {
    BirdsPage(
        uiState = MutableStateFlow(
            UIState.UI(
                BirdCategoryState(
                    mutableListOf(
                        BirdImage(
                            author = "Me",
                            category = "",
                            path = ""
                        )
                    )
                )
            )
        ), categoryState = MutableStateFlow(BirdCategoryState())
    ) {

    }
}
