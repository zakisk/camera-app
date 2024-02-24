package com.example.cameraapp.ui.upload_screen.components

/*
* Credit: https://medium.com/@eozsahin1993/custom-progress-bars-in-jetpack-compose-723afb60c81c
* */

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cameraapp.ui.theme.JaggedIce
import com.example.cameraapp.ui.theme.LocalSpacing
import com.example.cameraapp.ui.theme.Tacao
import com.example.cameraapp.ui.theme.William

@Composable
fun StripedProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    stripeColor: Color = Color.Tacao,
    stripeColorSecondary: Color = Color.JaggedIce,
    backgroundColor: Color = Color.JaggedIce,
    clipShape: Shape = RoundedCornerShape(16.dp)
) {
    Box(
        modifier = modifier
            .padding(horizontal = LocalSpacing.current.medium)
            .fillMaxWidth()
            .clip(clipShape)
            .background(backgroundColor)
            .height(10.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(clipShape)
                .background(brush = createStripeBrush(stripeColor, stripeColorSecondary, 2.dp))
                .fillMaxHeight()
                .fillMaxWidth(progress)
        )
    }
}

@Composable
private fun createStripeBrush(
    stripeColor: Color,
    stripeBg: Color,
    stripeWidth: Dp
): Brush {
    val stripeWidthPx = with(LocalDensity.current) { stripeWidth.toPx() }
    val brushSizePx = 3 * stripeWidthPx
    val stripeStart = stripeWidthPx / brushSizePx

    return Brush.linearGradient(
        stripeStart to stripeBg,
        stripeStart to stripeColor,
        start = Offset(0f, 0f),
        end = Offset(brushSizePx, brushSizePx),
        tileMode = TileMode.Repeated
    )
}