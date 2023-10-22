package com.hoppers.birdsafari.network

import com.hoppers.birdsafari.model.BirdImage


interface IApi {
    suspend fun getImages(body: String) : List<BirdImage>

     fun onCleared()
}