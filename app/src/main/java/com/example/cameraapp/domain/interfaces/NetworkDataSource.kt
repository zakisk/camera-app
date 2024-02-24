package com.example.cameraapp.domain.interfaces

import com.example.cameraapp.domain.models.Response
import okhttp3.MultipartBody

interface NetworkDataSource {
    suspend fun uploadImage(file: MultipartBody.Part): Response
}