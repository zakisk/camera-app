package com.example.cameraapp.ui.app_navigation


sealed class Screen(val route: String) {

    data object CameraScreen : Screen("camera")

    data object UploadScreen : Screen("upload")

}
