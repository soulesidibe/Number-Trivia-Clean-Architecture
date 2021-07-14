package com.soulesidibe.numbertrivia.data.network

interface NetworkInfo {
    suspend fun isConnected(): Boolean
}