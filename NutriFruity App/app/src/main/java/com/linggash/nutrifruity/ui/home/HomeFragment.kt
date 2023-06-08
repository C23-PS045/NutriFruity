package com.linggash.nutrifruity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.databinding.FragmentHomeBinding
import com.linggash.nutrifruity.ui.custom.CardComponent
import com.linggash.nutrifruity.ui.theme.PetitCochon
import com.linggash.nutrifruity.ui.theme.SpacingStandard

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cvTitle.setContent {
            CardComponent(
                borderColor = colorResource(R.color.orange_secondary),
                cardColor = colorResource( R.color.orange_primary) ,
                cardShape = CircleShape,
                borderSize = 5.dp
            ) {
                Text(
                    text = getString(R.string.nutrifruity),
                    color = Color.White,
                    fontFamily = PetitCochon,
                    fontSize = 48.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        binding.cvCameraCard.setContent {
            CardComponent(
                borderColor = colorResource(R.color.green_secondary),
                cardColor = colorResource(R.color.green_primary),
                enabled = true,
                onClick = {}
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.extraLarge)
                        .padding(horizontal = SpacingStandard)
                        .padding(top = SpacingStandard)
                ) {
                    Text(
                        text = getString(R.string.fruit_camera),
                        fontFamily = PetitCochon,
                        color = colorResource(R.color.brown_text),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = getString(R.string.scan_fruit),
                        fontFamily = PetitCochon,
                        color = colorResource(R.color.brown_text),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
        binding.cvListCard.setContent {
            CardComponent(
                borderColor = colorResource(R.color.red_secondary),
                cardColor = colorResource(R.color.red_primary),
                onClick = {},
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(SpacingStandard)
                ) {
                    Image(
                        painter = painterResource(R.drawable.banana),
                        contentDescription = stringResource(R.string.list_fruit),
                        Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(R.drawable.list),
                        contentDescription = stringResource(R.string.list_fruit),
                        Modifier.weight(1f)
                    )
                }
            }
        }
        binding.cvGameCard.setContent {
            CardComponent(
                borderColor = colorResource(R.color.cream_secondary),
                cardColor = colorResource(R.color.cream_primary),
                modifier = Modifier
                    .fillMaxWidth()

                ){
                Box(
                    modifier = Modifier
                        .padding(SpacingStandard)
                        .fillMaxSize()
                ){
                    Image(
                        painter = painterResource(R.drawable.white_strawberry),
                        contentDescription = stringResource(R.string.game),
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Image(
                        painter = painterResource(R.drawable.mark_question),
                        contentDescription = stringResource(R.string.game),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}