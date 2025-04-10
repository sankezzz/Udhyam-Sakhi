package com.example.udyam

import com.example.udyam.models.OrderResponse
import retrofit2.http.GET

interface OrderApiService {
    @GET("api/latest-order")
    suspend fun getLatestOrder(): OrderResponse
}
