package com.example.cameraapp.ui.camera_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.cameraapp.ui.theme.LocalSpacing
import com.example.cameraapp.ui.theme.Tacao

@Composable
fun Header(enabled: Boolean, onEndClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalSpacing.current.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Wade Warren",
            color = Color.White,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )

        AssistChip(
            onClick = onEndClicked,
            enabled = enabled,
            label = {
                Text(
                    text = "End",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )
            },
            shape = CircleShape,
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color.Tacao,
                disabledContainerColor = Color.Transparent
            )
        )
    }
}