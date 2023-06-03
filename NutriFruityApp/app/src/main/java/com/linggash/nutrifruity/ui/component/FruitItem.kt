package com.linggash.nutrifruity.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.ui.theme.BrownText
import com.linggash.nutrifruity.ui.theme.GreenPrimary
import com.linggash.nutrifruity.ui.theme.GreenSecondary
import com.linggash.nutrifruity.ui.theme.PetitCochon
import com.linggash.nutrifruity.ui.theme.SpacingStandard


@Composable
fun FruitItem(
    modifier: Modifier = Modifier,
    borderColor: Color,
    cardColor: Color,
    name: String,
    image: String
){
    CardComponent(
        borderColor = borderColor,
        cardColor = cardColor,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(SpacingStandard)
        ) {
            Text(
                text = name,
                fontFamily = PetitCochon,
                color = BrownText,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
            AsyncImage(
                model = image,
                contentDescription = name,
                modifier = modifier
                    .size(134.dp)
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
)
fun FruitItemPreview(){
    FruitItem(
        borderColor = GreenSecondary,
        cardColor = GreenPrimary,
        name = "Logo",
        image = "https://storage.googleapis.com/image-nutrifruity/1.png"
    )
}