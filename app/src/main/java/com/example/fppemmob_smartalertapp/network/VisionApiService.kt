package com.example.fppemmob_smartalertapp.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface VisionApiService {

    // Kita melakukan POST ke endpoint images:annotate
    @POST("v1/images:annotate")
    fun annotateImage(
        @Query("key") apiKey: String, // API Key nanti dimasukkan saat dipanggil
        @Body request: VisionRequest
    ): Call<VisionResponse>
}