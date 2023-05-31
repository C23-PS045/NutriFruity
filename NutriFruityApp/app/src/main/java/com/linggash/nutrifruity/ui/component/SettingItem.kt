package com.linggash.nutrifruity.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.linggash.nutrifruity.ui.theme.BrownText
import com.linggash.nutrifruity.ui.theme.OrangePrimary
import com.linggash.nutrifruity.ui.theme.OrangeThumb
import com.linggash.nutrifruity.ui.theme.PetitCochon

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    text: String
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontFamily = PetitCochon,
            color = BrownText,
            fontSize = 36.sp,
            textAlign = TextAlign.Start
        )
        Switch(
            colors = SwitchDefaults.colors(
                checkedBorderColor = OrangePrimary,
                checkedThumbColor = OrangeThumb,
                checkedTrackColor = OrangePrimary,
            ),
            checked = true,
            onCheckedChange ={

            }
        )
    }
}