package com.hoppers.birdsafari

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform