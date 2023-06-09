package com.linggash.nutrifruity.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.ui.theme.PetitCochon
import com.linggash.nutrifruity.ui.theme.SpacingStandard


@Composable
fun FruitItem(
    modifier: Modifier = Modifier,
    borderColor: Color,
    cardColor: Color,
    name: String,
    image: String,
    onClick: () -> Unit
){
    CardComponent(
        borderColor = borderColor,
        cardColor = cardColor,
        enabled = true,
        onClick = {onClick()},
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(SpacingStandard)
                .fillMaxSize()
        ) {
            Text(
                text = name,
                fontFamily = PetitCochon,
                color = colorResource(R.color.brown_text),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
            AsyncImage(
                model = image,
                contentDescription = name,
                modifier = modifier
                    .fillMaxWidth()
            )
            Spacer(modifier)
        }
    }
}

@Composable
@Preview(
    showBackground = true,
)
fun FruitItemPreview(){
    FruitItem(
        borderColor = colorResource(R.color.green_secondary),
        cardColor = colorResource(R.color.green_primary),
        name = "LOGO",
        image = "https://storage.googleapis.com/image-nutrifruity/1.png",
        onClick = {}
    )
}