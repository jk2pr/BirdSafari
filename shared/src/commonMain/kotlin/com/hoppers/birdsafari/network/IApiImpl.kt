package com.hoppers.birdsafari.network

import com.hoppers.birdsafari.model.BirdImage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class IApiImpl (private val client: HttpClient) : IApi {

    override suspend fun getImages(body: String): List<BirdImage> =
        client.get("https://sebastianaigner.github.io/demo-image-api/pictures.json")
            .body()

    override fun onCleared() {
        client.close()
    }

}

