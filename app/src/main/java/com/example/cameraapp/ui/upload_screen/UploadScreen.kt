package com.example.cameraapp.ui.upload_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cameraapp.CameraViewModel
import com.example.cameraapp.R
import com.example.cameraapp.ui.camera_screen.SwitchButtons
import com.example.cameraapp.ui.theme.JaggedIce
import com.example.cameraapp.ui.theme.LocalCustomShapes
import com.example.cameraapp.ui.theme.LocalSpacing
import com.example.cameraapp.ui.theme.Tacao
import com.example.cameraapp.ui.theme.TimberGreen
import com.example.cameraapp.ui.theme.William
import com.example.cameraapp.ui.upload_screen.components.PhotoItem
import com.example.cameraapp.ui.upload_screen.components.StripedProgressIndicator

@Composable
fun UploadScreen(viewModel: CameraViewModel) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.William)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = LocalSpacing.current.medium),
                text = "${viewModel.imagesCount()} Global Images",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            LazyVerticalGrid(
                modifier = Modifier.padding(LocalSpacing.current.medium),
                columns = GridCells.Fixed(2)
            ) {
                items(viewModel.getPhotos()) {
                    PhotoItem(photoItem = it)
                }
            }

            SwitchButtons(
                global = "Global(${viewModel.imagesCount()})",
                closeUp = "Close Up(${viewModel.imagesCount()})"
            )
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(LocalSpacing.current.medium)
                .fillMaxWidth(),
            onClick = { viewModel.uploadImages(context) },
            shape = LocalCustomShapes.current.smallShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Tacao, contentColor = Color.TimberGreen
            ),
            enabled = viewModel.imagesCount() > 0
        ) {
            Text(
                modifier = Modifier.padding(LocalSpacing.current.small),
                text = "Upload",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }

    if (viewModel.showDialog) {
        ProgressDialog(
            viewModel.progress,
            viewModel.isProgressEnd,
            viewModel.response?.succeeded ?: 0,
            viewModel.response?.failed ?: 0
        ) {
            viewModel.showDialog = false
            viewModel.progress = 0F
            viewModel.response = null
            viewModel.isProgressEnd = true
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressDialog(
    progress: Float,
    isProgressEnd: Boolean,
    succeed: Int,
    failed: Int,
    onDismiss: () -> Unit
) {
    AlertDialog(onDismissRequest = { }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.medium)
                .background(
                    color = if (isProgressEnd) Color.JaggedIce else Color.William,
                    shape = LocalCustomShapes.current.smallShape
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
            if (!isProgressEnd) {
                StripedProgressIndicator(progress = progress)

                Text(
                    modifier = Modifier.padding(top = LocalSpacing.current.medium),
                    text = "${(progress * 100).toInt()}% in progress",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            } else {
                Icon(
                    modifier = Modifier.size(56.dp),
                    painter = painterResource(id = R.drawable.dialog_done),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Text(
                    modifier = Modifier.padding(top = LocalSpacing.current.medium),
                    text = "Successful",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = Color.TimberGreen
                )

                Text(
                    modifier = Modifier.padding(
                        top = LocalSpacing.current.medium,
                        start = LocalSpacing.current.large,
                        end = LocalSpacing.current.medium
                    ),
                    text = "Your $succeed image are uploaded successfully and $failed failed.",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.William
                )

                Button(
                    modifier = Modifier.padding(top = LocalSpacing.current.medium),
                    onClick = onDismiss, // its effect will be same as onDismiss action
                    shape = LocalCustomShapes.current.smallShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.TimberGreen, contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Go to home",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
        }
    }
}