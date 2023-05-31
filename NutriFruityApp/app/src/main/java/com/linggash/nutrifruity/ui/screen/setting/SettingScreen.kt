package com.linggash.nutrifruity.ui.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.ui.component.CardComponent
import com.linggash.nutrifruity.ui.component.SettingItem
import com.linggash.nutrifruity.ui.theme.GrayBackground
import com.linggash.nutrifruity.ui.theme.OrangePrimary
import com.linggash.nutrifruity.ui.theme.OrangeSecondary
import com.linggash.nutrifruity.ui.theme.PetitCochon
import com.linggash.nutrifruity.ui.theme.SpacingStandard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
) {
    val spacing = SpacingStandard
    Scaffold(
        containerColor = GrayBackground,
        topBar = { TopBar(modifier) }
    ) { innerPadding ->
        Column(
            modifier.padding(innerPadding)
                .padding(horizontal = spacing)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = MaterialTheme.shapes.large,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(spacing),
            ) {
                Column(
                    modifier.padding(spacing)
                ) {
                    SettingItem(text = "MUSIK")
                    SettingItem(text = "SUARA")
                }
            }
        }
    }
}

@Composable
fun TopBar(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .padding(25.dp)
    ){
        CardComponent(
            borderColor = OrangeSecondary,
            borderSize = 5.dp,
            cardColor = OrangePrimary,
            modifier = modifier.fillMaxWidth(),
            cardShape = CircleShape
        ) {
            Text(
                text = stringResource(R.string.pengaturan),
                color = Color.White,
                fontFamily = PetitCochon,
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true
)
private fun SettingScreenPreview() {
    SettingScreen()
}