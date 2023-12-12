package com.hoppers.birdsafari.android

import BirdSafariAppTheme
import androidx.compose.runtime.Composable
import com.hoppers.birdsafari.android.pages.BirdsPage
import com.hoppers.birdsafari.android.viewmodels.BirdViewModel
import com.hoppers.birdsafari.repositories.BirdRepository

@Composable
fun App() {

    BirdSafariAppTheme {

        val birdsViewModel = BirdViewModel(BirdRepository())
        BirdsPage(
            uiState = birdsViewModel.birdState,
            categoryState = birdsViewModel.categoryState,
            onSelectCategory = { birdsViewModel.selectCategory(it) })
    }
}