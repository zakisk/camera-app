package com.example.cameraapp

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cameraapp.domain.models.DataState
import com.example.cameraapp.domain.models.Responses
import com.example.cameraapp.domain.use_cases.UploadImageUseCase
import com.example.cameraapp.ui.camera_screen.components.Position
import com.example.cameraapp.ui.camera_screen.components.Position.Frontal
import com.example.cameraapp.ui.camera_screen.components.Position.Left
import com.example.cameraapp.ui.camera_screen.components.Position.Right
import com.example.cameraapp.ui.camera_screen.components.Position.Top
import com.example.cameraapp.ui.upload_screen.components.PhotoItem
import com.example.cameraapp.utils.showToast
import com.example.cameraapp.utils.toFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var topImage by mutableStateOf<Bitmap?>(null)
    private var leftImage by mutableStateOf<Bitmap?>(null)
    private var frontalImage by mutableStateOf<Bitmap?>(null)
    private var rightImage by mutableStateOf<Bitmap?>(null)
    var isEndButtonEnabled by mutableStateOf(false)
    var showDialog by mutableStateOf(false)
    var progress by mutableStateOf(0F)
    var isProgressEnd by mutableStateOf(true)
    var response: Responses? = null

    fun setImage(position: Position, bitmap: Bitmap) {
        when (position) {
            Top -> topImage = bitmap
            Left -> leftImage = bitmap
            Frontal -> frontalImage = bitmap
            Right -> rightImage = bitmap
        }
    }

    fun getImageByPosition(position: Position): Bitmap? {
        return when (position) {
            Top -> topImage
            Left -> leftImage
            Frontal -> frontalImage
            Right -> rightImage
        }
    }

    fun imagesCount(): Int {
        var count = 0
        if (topImage != null) count++
        if (leftImage != null) count++
        if (frontalImage != null) count++
        if (rightImage != null) count++
        return count
    }

    fun getPhotos(): List<PhotoItem> {
        val photos = mutableListOf<PhotoItem>()
        topImage?.let { photos.add(PhotoItem(position = Top, bitmap = it)) }
        leftImage?.let { photos.add(PhotoItem(position = Left, bitmap = it)) }
        frontalImage?.let { photos.add(PhotoItem(position = Frontal, bitmap = it)) }
        rightImage?.let { photos.add(PhotoItem(position = Right, bitmap = it)) }

        return photos
    }

    fun uploadImages(context: Context) {
        showDialog = true
        isProgressEnd = false
        val files = getFiles(context)
        var fileCount = 0
        uploadImageUseCase(files).onEach { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    context.showToast("ERROR: ${dataState.message}")
                    fileCount++
                    if (fileCount == files.size) {
                        progress = 1f
                        delay(100)
                        isProgressEnd = true
                    }
                }

                is DataState.Progress -> {
                    progress = dataState.progress
                    fileCount++
                }

                is DataState.Success -> {
                    fileCount++
                    dataState.data?.let {
                        response = it
                        progress = 1f
                        delay(400)
                        isProgressEnd = true
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getFiles(context: Context): List<File> {
        val files = mutableListOf<File>()
        topImage?.let {
            val file = it.toFile(context)
            if (file != null) {
                files.add(file)
            } else {
                context.showToast("Failed to get file of top image")
            }
        }

        leftImage?.let {
            val file = it.toFile(context)
            if (file != null) {
                files.add(file)
            } else {
                context.showToast("Failed to get file of left image")
            }
        }

        frontalImage?.let {
            val file = it.toFile(context)
            if (file != null) {
                files.add(file)
            } else {
                context.showToast("Failed to get file of frontal image")
            }
        }

        rightImage?.let {
            val file = it.toFile(context)
            if (file != null) {
                files.add(file)
            } else {
                context.showToast("Failed to get file of right image")
            }
        }

        return files
    }
}