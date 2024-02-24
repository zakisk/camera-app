package com.example.cameraapp.ui.camera_screen.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.cameraapp.ui.theme.LocalCustomShapes
import com.example.cameraapp.ui.theme.LocalSpacing
import com.example.cameraapp.ui.theme.Tacao

@Composable
fun Camera(modifier: Modifier, onPhotoCaptured: (Bitmap) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }

    Box(
        modifier = modifier
            .background(
                color = androidx.compose.ui.graphics.Color.Transparent,
                shape = LocalCustomShapes.current.mediumShape
            )
            .clip(LocalCustomShapes.current.mediumShape)
    ) {
        AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
            PreviewView(context).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                setBackgroundColor(Color.BLACK)
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                scaleType = PreviewView.ScaleType.FILL_START
            }.also { previewView ->
                previewView.controller = cameraController
                cameraController.bindToLifecycle(lifecycleOwner)
            }
        })

        Icon(
            modifier = Modifier
                .padding(bottom = LocalSpacing.current.medium)
                .size(64.dp)
                .align(Alignment.BottomCenter)
                .clickable {
                    capturePhoto(context, cameraController) { bitmap ->
                        onPhotoCaptured(bitmap)
                    }
                },
            imageVector = Icons.Filled.Camera,
            contentDescription = null,
            tint = androidx.compose.ui.graphics.Color.Tacao.copy(alpha = .8F),
        )
    }
}

private fun capturePhoto(
    context: Context, cameraController: LifecycleCameraController, onPhotoCaptured: (Bitmap) -> Unit
) {
    val mainExecutor = ContextCompat.getMainExecutor(context)

    cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            val bitmap: Bitmap = image.toBitmap()
            onPhotoCaptured(bitmap)
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e("CameraContent", "Error capturing image", exception)
        }
    })
}

enum class Position {
    Top, Left, Frontal, Right
}