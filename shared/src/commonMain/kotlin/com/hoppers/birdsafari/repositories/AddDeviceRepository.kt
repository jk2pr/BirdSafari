package com.hoppers.birdsafari.repositories

import com.hoppers.birdsafari.network.IApi
import com.hoppers.birdsafari.network.IApiImpl
import com.hoppers.birdsafari.network.KtorClient

class BirdRepository {
    val api =IApiImpl(KtorClient.getKtorClient())
    suspend fun getImages() =
        api.getImages(body = "https://sebastianaigner.github.io/demo-image-api/pictures.json")


    fun close(){
        api.onCleared()
    }
}
