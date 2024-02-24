package com.example.cameraapp.domain.models

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("originalname") var originalName: String? = null,
    @SerializedName("filename") var fileName: String? = null,
    @SerializedName("location") var location: String? = null
)

data class Responses(
    var responses: List<Response> = emptyList(),
    var succeeded: Int = 0,
    var failed: Int = 0
)