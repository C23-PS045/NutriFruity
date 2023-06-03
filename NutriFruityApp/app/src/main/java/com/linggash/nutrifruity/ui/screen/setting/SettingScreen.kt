package com.linggash.nutrifruity.ui.screen.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.linggash.nutrifruity.ui.component.SettingItem
import com.linggash.nutrifruity.ui.theme.SpacingStandard

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
) {
    val spacing = SpacingStandard
    Column(
        modifier.padding(horizontal = spacing)
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

@Composable
@Preview(
    showBackground = true
)
private fun SettingScreenPreview() {
    SettingScreen()
}