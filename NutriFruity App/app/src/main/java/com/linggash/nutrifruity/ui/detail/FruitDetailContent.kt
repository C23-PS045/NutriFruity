package com.linggash.nutrifruity.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.database.Benefit
import com.linggash.nutrifruity.database.Nutrition
import com.linggash.nutrifruity.ui.theme.NanumPen
import com.linggash.nutrifruity.ui.theme.PetitCochon
import com.linggash.nutrifruity.ui.theme.SpacingLarge
import com.linggash.nutrifruity.ui.theme.SpacingStandard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitDetailContent(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    nutrition: List<Nutrition>? = null,
    benefit: List<Benefit>? = null,
    color: Color = colorResource(R.color.green_primary),
    onClick: () -> Unit
){
    val nutritionText = nutrition?.joinToString { it.nutrition }
    Scaffold(
        topBar = { TopBarDetail(name, modifier, color, onClick = { onClick() }) },
        containerColor = color,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(SpacingLarge)
                .verticalScroll(rememberScrollState())
        ) {

            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                contentScale = ContentScale.FillWidth,
                modifier = modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.nutrition),
                color = Color.White,
                fontFamily = PetitCochon,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .fillMaxWidth()
            )
            if (nutritionText != null) {
                Text(
                    text = nutritionText,
                    color = Color.White,
                    fontFamily = NanumPen,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start,
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
            Text(
                text = stringResource(R.string.benefit) + " $name",
                color = Color.White,
                fontFamily = PetitCochon,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .fillMaxWidth()
            )
            benefit?.forEach {
                Row {
                    Text(
                        text = Typography.bullet + "\t",
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Text(
                        text = it.benefit,
                        color = Color.White,
                        fontFamily = NanumPen,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Start,
                        modifier = modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarDetail(
    name: String,
    modifier: Modifier,
    color: Color,
    onClick: () -> Unit
){
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(color),
        title = {
            Text(
                text = name,
                color = Color.White,
                fontFamily = PetitCochon,
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
            )},
        navigationIcon = {
            IconButton(
                onClick = {
                    onClick()
                }
            ) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        modifier = modifier.padding(top = 10.dp)
    )
}
