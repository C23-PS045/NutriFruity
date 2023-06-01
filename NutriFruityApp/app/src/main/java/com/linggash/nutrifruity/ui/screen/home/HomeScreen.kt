package com.linggash.nutrifruity.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.ui.component.CardComponent
import com.linggash.nutrifruity.ui.navigation.Screen
import com.linggash.nutrifruity.ui.theme.BrownText
import com.linggash.nutrifruity.ui.theme.CreamPrimary
import com.linggash.nutrifruity.ui.theme.CreamSecondary
import com.linggash.nutrifruity.ui.theme.GrayBackground
import com.linggash.nutrifruity.ui.theme.GreenPrimary
import com.linggash.nutrifruity.ui.theme.GreenSecondary
import com.linggash.nutrifruity.ui.theme.OrangePrimary
import com.linggash.nutrifruity.ui.theme.OrangeSecondary
import com.linggash.nutrifruity.ui.theme.PetitCochon
import com.linggash.nutrifruity.ui.theme.RedPrimary
import com.linggash.nutrifruity.ui.theme.RedSecondary
import com.linggash.nutrifruity.ui.theme.SpacingStandard

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
){
    val spacing = SpacingStandard
    Column(
        modifier = modifier
            .padding(horizontal = spacing)
            .padding(bottom = spacing)
            .fillMaxWidth()
    ){
        CardComponent(
            borderColor = GreenSecondary,
            cardColor = GreenPrimary,
            modifier = modifier.weight(3f)
        ){
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraLarge)
                    .padding(horizontal = spacing)
                    .padding(top = spacing)
            ) {
                Text(
                    text = stringResource(R.string.kamera_buah),
                    fontFamily = PetitCochon,
                    color = BrownText,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(R.string.ayo_pindai_buah),
                    fontFamily = PetitCochon,
                    color = BrownText,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                )
            }
        }
        Spacer(modifier = modifier.height(spacing))
        Text(
            text = stringResource(R.string.lainnya),
            fontFamily = PetitCochon,
            color = BrownText,
            fontSize = 24.sp,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = modifier.height(spacing))
        Row(
            modifier = modifier
                .weight(2.2f)
                .fillMaxWidth()
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .fillMaxWidth()
            )
            {
                CardComponent(
                    borderColor = RedSecondary,
                    cardColor = RedPrimary,
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            navController.navigate(Screen.FruitList.route)
                        }
                ){

                }
                Spacer(modifier = modifier.height(spacing))
                Text(
                    text = stringResource(R.string.daftar_buah),
                    fontFamily = PetitCochon,
                    color = BrownText,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = modifier.width(spacing))
            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .fillMaxWidth()
            )
            {
                CardComponent(
                    borderColor = CreamSecondary,
                    cardColor = CreamPrimary,
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                ){

                }
                Spacer(modifier = modifier.height(spacing))
                Text(
                    text = stringResource(R.string.tebak_buah),
                    fontFamily = PetitCochon,
                    color = BrownText,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true
)
private fun HomePreview(){
    HomeScreen(navController = rememberNavController())
}