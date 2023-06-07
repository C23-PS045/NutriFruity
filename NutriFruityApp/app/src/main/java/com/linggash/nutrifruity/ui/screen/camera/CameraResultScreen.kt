package com.linggash.nutrifruity.ui.screen.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.linggash.nutrifruity.R

@Composable
fun CameraResultScreen(
    modifier: Modifier = Modifier,
    path: String,
){
    Column(modifier.fillMaxSize()) {
        Image(painter = painterResource(R.drawable.mark_question), contentDescription = "", modifier.fillMaxWidth())
    }
}