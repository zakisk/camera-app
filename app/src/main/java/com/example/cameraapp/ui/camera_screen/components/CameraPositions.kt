package com.example.cameraapp.ui.camera_screen.components

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cameraapp.R
import com.example.cameraapp.CameraViewModel
import com.example.cameraapp.ui.theme.LocalSpacing
import com.example.cameraapp.ui.theme.TimberGreen

@Composable
fun CameraPositions(
    viewModel: CameraViewModel, position: Position, onPositionChange: (Position) -> Unit
) {
    val modifiers = backgroundModifiers(position)
    LazyRow {
        items(modifiers) {
            val bitmap = viewModel.getImageByPosition(it.position)
            PositionItem(positionData = it, bitmap = bitmap, onPositionChange = onPositionChange)
        }
    }
}

@Composable
fun PositionItem(
    positionData: PositionData, bitmap: Bitmap?, onPositionChange: (Position) -> Unit
) {

    Box(modifier = positionData.modifier
        .fillMaxHeight()
        .width(104.dp)
        .clickable(
            interactionSource = null, indication = null
        ) { onPositionChange(positionData.position) }) {

        if (bitmap != null) {
            BitmapImage(positionData = positionData, bitmap = bitmap)
        } else {
            ImageItem(positionData = positionData)
        }
    }
}

@Composable
fun ImageItem(positionData: PositionData) {
    Column(
        modifier = positionData.modifier
            .fillMaxHeight()
            .width(104.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = positionData.modifier.fillMaxSize(.6F),
            painter = painterResource(id = positionData.id),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Text(
            modifier = Modifier.padding(LocalSpacing.current.small),
            text = positionData.position.name,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun BitmapImage(positionData: PositionData, bitmap: Bitmap) {
    Box(
        modifier = positionData.modifier
            .fillMaxHeight()
            .width(104.dp)
    ) {
        Image(
            modifier = positionData.modifier.fillMaxSize(),
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Text(
            modifier = Modifier
                .padding(bottom = LocalSpacing.current.medium)
                .align(Alignment.BottomCenter),
            text = positionData.position.name,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

fun backgroundModifiers(selectedPosition: Position): List<PositionData> {
    val topStart = RoundedCornerShape(topStart = 12.dp)
    val topEnd = RoundedCornerShape(topEnd = 12.dp)
    val modifiers = mutableListOf<PositionData>()
    when (selectedPosition) {
        Position.Top -> {
            modifiers.add(PositionData(modifier = Modifier.background(color = Color.Transparent)))
            modifiers.add(
                PositionData(
                    modifier = Modifier
                        .background(color = Color.TimberGreen, shape = topStart)
                        .clip(topStart)
                )
            )
            modifiers.add(PositionData(modifier = Modifier.background(color = Color.TimberGreen)))
            modifiers.add(PositionData(modifier = Modifier.background(color = Color.TimberGreen)))
        }

        Position.Left -> {
            modifiers.add(
                PositionData(
                    modifier = Modifier
                        .background(color = Color.TimberGreen, shape = topEnd)
                        .clip(topEnd)
                )
            )
            modifiers.add(PositionData(modifier = Modifier.background(color = Color.Transparent)))
            modifiers.add(
                PositionData(
                    modifier = Modifier
                        .background(color = Color.TimberGreen, shape = topStart)
                        .clip(topStart)
                )
            )
            modifiers.add(PositionData(modifier = Modifier.background(color = Color.TimberGreen)))
        }

        Position.Frontal -> {
            modifiers.add(PositionData(modifier = Modifier.background(color = Color.TimberGreen)))
            modifiers.add(
                PositionData(
                    modifier = Modifier
                        .background(color = Color.TimberGreen, shape = topEnd)
                        .clip(topEnd)
                )
            )
            modifiers.add(PositionData(modifier = Modifier.background(color = Color.Transparent)))
            modifiers.add(
                PositionData(
                    modifier = Modifier
                        .background(color = Color.TimberGreen, shape = topStart)
                        .clip(topStart)
                )
            )
        }

        Position.Right -> {
            modifiers.add(PositionData(modifier = Modifier.background(color = Color.TimberGreen)))
            modifiers.add(PositionData(modifier = Modifier.background(color = Color.TimberGreen)))
            modifiers.add(
                PositionData(
                    modifier = Modifier
                        .background(color = Color.TimberGreen, shape = topEnd)
                        .clip(topEnd)
                )
            )
            modifiers.add(PositionData(modifier = Modifier.background(color = Color.Transparent)))
        }
    }

    modifiers[0] = modifiers[0].apply {
        id = R.drawable.top
        position = Position.Top
    }
    modifiers[1] = modifiers[1].apply {
        id = R.drawable.left
        position = Position.Left
    }
    modifiers[2] = modifiers[2].apply {
        id = R.drawable.frontal
        position = Position.Frontal
    }
    modifiers[3] = modifiers[3].apply {
        id = R.drawable.right
        position = Position.Right
    }
    return modifiers
}

data class PositionData(
    val modifier: Modifier, @DrawableRes var id: Int = 0, var position: Position = Position.Top
)