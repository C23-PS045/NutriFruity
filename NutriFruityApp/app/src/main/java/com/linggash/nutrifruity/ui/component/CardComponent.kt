package com.linggash.nutrifruity.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardComponent(
    modifier: Modifier = Modifier,
    borderColor: Color,
    borderSize: Dp = 20.dp,
    cardColor: Color,
    cardShape: Shape = MaterialTheme.shapes.extraLarge,
    content: @Composable (ColumnScope) -> Unit
){
    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = cardShape,
        modifier = modifier
            .background(borderColor, cardShape)
            .padding(bottom = borderSize),
    ){
        content(this)
    }
}