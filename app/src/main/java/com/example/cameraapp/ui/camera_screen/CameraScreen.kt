package com.example.cameraapp.ui.camera_screen

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cameraapp.CameraViewModel
import com.example.cameraapp.ui.app_navigation.Screen
import com.example.cameraapp.ui.camera_screen.components.Camera
import com.example.cameraapp.ui.camera_screen.components.CameraPositions
import com.example.cameraapp.ui.camera_screen.components.Header
import com.example.cameraapp.ui.camera_screen.components.NoPermissionContent
import com.example.cameraapp.ui.camera_screen.components.Position
import com.example.cameraapp.ui.theme.JaggedIce
import com.example.cameraapp.ui.theme.LocalSpacing
import com.example.cameraapp.ui.theme.Tacao
import com.example.cameraapp.ui.theme.TimberGreen
import com.example.cameraapp.ui.theme.William
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(navController: NavHostController, viewModel: CameraViewModel) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    LaunchedEffect(key1 = null) {
        cameraPermissionState.launchPermissionRequest()
    }
    var position by remember { mutableStateOf(Position.Top) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.William),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (cameraPermissionState.status.isGranted) {
            Header(viewModel.isEndButtonEnabled) { navController.navigate(Screen.UploadScreen.route) }
            CameraScreenContent(onPhotoCaptured = {
                viewModel.setImage(position, it)
                viewModel.isEndButtonEnabled = true
            })
            SwitchButtons()
            CameraPositions(
                viewModel = viewModel, position = position
            ) { position = it }
        } else {
            NoPermissionContent(onRequestPermission = cameraPermissionState::launchPermissionRequest)
        }
    }
}

@Composable
fun CameraScreenContent(onPhotoCaptured: (Bitmap) -> Unit) {
    Camera(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.65F)
            .padding(
                horizontal = LocalSpacing.current.medium, vertical = LocalSpacing.current.small
            ), onPhotoCaptured = onPhotoCaptured
    )
}

@Composable
fun SwitchButtons(global: String = "Global", closeUp: String = "Close Up") {
    var selected by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier
            .padding(vertical = LocalSpacing.current.medium)
            .fillMaxWidth(.7F)
            .background(color = Color.JaggedIce, shape = CircleShape)
    ) {
        Text(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(.5F)
                .background(
                    color = if (selected == 0) Color.Tacao else Color.Transparent,
                    shape = CircleShape
                )
                .padding(LocalSpacing.current.small)
                .clickable(indication = null, interactionSource = null) { selected = 0 },
            text = global,
            color = Color.TimberGreen,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .background(
                    color = if (selected == 1) Color.Tacao else Color.Transparent,
                    shape = CircleShape
                )
                .padding(LocalSpacing.current.small)
                .clickable(indication = null, interactionSource = null) { selected = 1 },
            text = closeUp,
            color = Color.TimberGreen,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
    }
}