package com.linggash.nutrifruity.ui.screen.camera

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import java.io.File

@Composable
fun CameraResultScreen(
    modifier: Modifier = Modifier,
    file: File,
){
    AsyncImage(
        model = BitmapFactory.decodeFile(file.path),
        contentDescription = "Buah",
        modifier = modifier.fillMaxWidth()
    )
}