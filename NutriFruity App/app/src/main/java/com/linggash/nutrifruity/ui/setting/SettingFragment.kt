package com.linggash.nutrifruity.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.databinding.FragmentSettingBinding
import com.linggash.nutrifruity.ui.component.CardComponent
import com.linggash.nutrifruity.ui.theme.PetitCochon

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cvTitle.setContent {
            CardComponent(
                borderColor = colorResource(R.color.orange_secondary),
                cardColor = colorResource( R.color.orange_primary) ,
                cardShape = CircleShape,
                borderSize = 5.dp
            ) {
                Text(
                    text = getString(R.string.setting),
                    color = Color.White,
                    fontFamily = PetitCochon,
                    fontSize = 48.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        binding.switchMusic.setContent {
            Switch(
                colors = SwitchDefaults.colors(
                    checkedBorderColor = colorResource( R.color.orange_primary),
                    checkedThumbColor = colorResource( R.color.orange_third),
                    checkedTrackColor = colorResource( R.color.orange_primary),
                ),
                checked = true,
                onCheckedChange ={

                }
            )
        }
        binding.switchSound.setContent {
            Switch(
                colors = SwitchDefaults.colors(
                    checkedBorderColor = colorResource( R.color.orange_primary),
                    checkedThumbColor = colorResource( R.color.orange_third),
                    checkedTrackColor = colorResource( R.color.orange_primary),
                ),
                checked = true,
                onCheckedChange ={

                }
            )
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}