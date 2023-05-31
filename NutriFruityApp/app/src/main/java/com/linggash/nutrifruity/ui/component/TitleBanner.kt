package com.linggash.nutrifruity.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linggash.nutrifruity.ui.theme.PetitCochon
import com.linggash.nutrifruity.ui.theme.orangePrimary
import com.linggash.nutrifruity.ui.theme.orangeSecondary

@Composable
fun TitleBanner(
    title : String,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(orangeSecondary)
            .fillMaxWidth()
            .padding(bottom = 5.dp)

    ) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(orangePrimary)
                .fillMaxWidth()
                .padding(top = 5.dp)
        ){
            Text(
                text = title,
                color = Color.White,
                fontFamily = PetitCochon,
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.align(Alignment.Center)
            )
        }
    }
}