package com.example.cameraapp.data.services

import com.example.cameraapp.domain.models.Response
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NetworkService {

    @Multipart
    @POST("/api/v1/files/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): Response
}