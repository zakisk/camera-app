package com.example.cameraapp.ui.app_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cameraapp.ui.camera_screen.CameraScreen
import com.example.cameraapp.CameraViewModel
import com.example.cameraapp.ui.upload_screen.UploadScreen

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(), viewModel: CameraViewModel
) {
    NavHost(
        navController = navController, startDestination = Screen.CameraScreen.route
    ) {
        composable(route = Screen.CameraScreen.route) {
            CameraScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.UploadScreen.route) {
            UploadScreen(viewModel = viewModel)
        }
    }
}