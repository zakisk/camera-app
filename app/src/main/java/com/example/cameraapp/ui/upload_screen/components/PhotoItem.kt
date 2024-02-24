package com.example.cameraapp.ui.upload_screen.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.cameraapp.R
import com.example.cameraapp.ui.camera_screen.components.Position
import com.example.cameraapp.ui.theme.LocalCustomShapes
import com.example.cameraapp.ui.theme.LocalSpacing
import com.example.cameraapp.ui.theme.MountainMeadow

data class PhotoItem(val position: Position, val bitmap: Bitmap)

@Composable
fun PhotoItem(photoItem: PhotoItem) {
    Box(
        modifier = Modifier
            .padding(LocalSpacing.current.small)
            .fillMaxWidth()
            .height(160.dp)
            .background(color = Color.Transparent, shape = LocalCustomShapes.current.mediumShape)
            .clip(LocalCustomShapes.current.mediumShape)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Transparent, shape = LocalCustomShapes.current.mediumShape
                )
                .clip(LocalCustomShapes.current.mediumShape),
            bitmap = photoItem.bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        val gradient = Brush.verticalGradient(
            listOf(
                Color.MountainMeadow.copy(alpha = .1F),
                Color.MountainMeadow.copy(alpha = .3F),
                Color.MountainMeadow.copy(alpha = .5F)
            )
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradient, shape = LocalCustomShapes.current.mediumShape)
                .clip(LocalCustomShapes.current.mediumShape),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .border(
                        width = 2.dp,
                        shape = LocalCustomShapes.current.smallShape,
                        color = Color.White
                    )
                    .padding(LocalSpacing.current.small),
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = null,
                tint = Color.White
            )
        }

        Text(
            modifier = Modifier
                .padding(bottom = LocalSpacing.current.medium)
                .align(Alignment.BottomCenter),
            text = photoItem.position.name,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun MinimalDialog(isUploading: Boolean, uploadPercent: Float, onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = !isUploading,
            dismissOnClickOutside = !isUploading
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

        }
    }
}