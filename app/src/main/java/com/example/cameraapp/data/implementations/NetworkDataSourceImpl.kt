package com.example.cameraapp.data.implementations

import com.example.cameraapp.data.services.NetworkService
import com.example.cameraapp.domain.interfaces.NetworkDataSource
import com.example.cameraapp.domain.models.Response
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val networkService: NetworkService
) : NetworkDataSource {
    override suspend fun uploadImage(file: MultipartBody.Part): Response {
        return networkService.uploadImage(file)
    }
}