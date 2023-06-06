package com.linggash.nutrifruity.ui.screen.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.model.Benefit
import com.linggash.nutrifruity.model.Nutrition
import com.linggash.nutrifruity.ui.common.UiState
import com.linggash.nutrifruity.ui.theme.GreenPrimary
import com.linggash.nutrifruity.ui.theme.NanumPen
import com.linggash.nutrifruity.ui.theme.PetitCochon
import com.linggash.nutrifruity.ui.theme.SpacingLarge

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    fruitId : Long,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading ->{
                viewModel.getFruitById(fruitId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    modifier = modifier,
                    name = data.name.uppercase(),
                    imageUrl = data.photoUrl,
                    nutrition = data.nutrition,
                    benefit = data.benefit
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    modifier: Modifier,
    name: String,
    imageUrl: String,
    nutrition: List<Nutrition>,
    benefit: List<Benefit>,
){
    val nutritionText = nutrition.joinToString { it.nutrition }
    Scaffold(
        topBar = { TopBarDetail(name, modifier) },
        containerColor = GreenPrimary,
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
                text = stringResource(R.string.nutrisi),
                color = Color.White,
                fontFamily = PetitCochon,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .fillMaxWidth()
            )
            Text(
                text = nutritionText,
                color = Color.White,
                fontFamily = NanumPen,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.manfaat) + " $name",
                color = Color.White,
                fontFamily = PetitCochon,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .fillMaxWidth()
            )
            benefit.forEach {
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
    modifier: Modifier
){
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = GreenPrimary),
        title = {
            Text(
                text = name,
                color = Color.White,
                fontFamily = PetitCochon,
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(SpacingLarge)
            )},
        navigationIcon = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back_to_list)
                )
            }
        },
        modifier = modifier.padding(SpacingLarge)
    )
}
