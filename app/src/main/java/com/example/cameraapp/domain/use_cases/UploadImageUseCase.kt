package com.example.cameraapp.domain.use_cases

import com.example.cameraapp.domain.interfaces.NetworkDataSource
import com.example.cameraapp.domain.models.DataState
import com.example.cameraapp.domain.models.Response
import com.example.cameraapp.domain.models.Responses
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    operator fun invoke(files: List<File>): Flow<DataState<Responses>> = flow {
        val responses = Responses()
        val resps = mutableListOf<Response>()
        val percentPerFile = 1F / files.size
        files.forEachIndexed { index, file ->
            try {
                val part =
                    MultipartBody.Part.createFormData("file", file.name, file.asRequestBody())
                val response = networkDataSource.uploadImage(part)
                resps.add(response)
                emit(DataState.Progress(progress = (index + 1) * percentPerFile))
                responses.succeeded++
            } catch (e: IOException) {
                e.printStackTrace()
                emit(DataState.Error(e.localizedMessage ?: "unspecified message"))
                // processing of one file didn't succeed but completed
                emit(DataState.Progress(progress = (index + 1) * percentPerFile))
                responses.failed++
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(DataState.Error("HTTP Status Code: ${e.code()}\n${e.localizedMessage}"))
                // processing of one file didn't succeed but completed
                emit(DataState.Progress(progress = (index + 1) * percentPerFile))
                responses.failed++
            }
        }
        responses.responses = resps
        emit(DataState.Success(responses))
    }
}