package com.linggash.nutrifruity.ui.home

import android.content.Intent
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.databinding.FragmentHomeBinding
import com.linggash.nutrifruity.ui.camera.CameraActivity
import com.linggash.nutrifruity.ui.component.CardComponent
import com.linggash.nutrifruity.ui.game.GameActivity
import com.linggash.nutrifruity.ui.list.FruitListActivity
import com.linggash.nutrifruity.ui.theme.PetitCochon
import com.linggash.nutrifruity.ui.theme.SpacingStandard

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()
        sp.setOnLoadCompleteListener{ _, _, status ->
            if (status == 0){
                spLoaded = true
            }else {
                Toast.makeText(requireActivity(), "Gagal load", Toast.LENGTH_SHORT).show()
            }
        }
        soundId = sp.load(requireContext(), R.raw.btn, 1)

        val modifier = Modifier
        binding.cvTitle.setContent {
            Image(
                painter = painterResource(R.drawable.nutrifruity_text),
                contentDescription = stringResource(R.string.nutrifruity),
                modifier = modifier.fillMaxWidth()
            )
        }
        binding.cvCameraCard.setContent {
            CardComponent(
                borderColor = colorResource(R.color.green_secondary),
                cardColor = colorResource(R.color.green_primary),
                enabled = true,
                onClick = {
                    if (spLoaded) {
                        sp.play(soundId, 1f, 1f, 0, 0, 1f)
                    }
                    val intent = Intent(requireActivity(), CameraActivity::class.java)
                    startActivity(intent)
                }
            ){
                Box(modifier = modifier.fillMaxSize()){
                    Image(
                        painter = painterResource(R.drawable.orange),
                        contentDescription = "",
                        modifier = modifier
                            .align(Alignment.BottomEnd)
                            .size(180.dp)
                    )
                    Column(
                        modifier = modifier
                            .clip(MaterialTheme.shapes.extraLarge)
                            .padding(horizontal = SpacingStandard)
                            .padding(top = SpacingStandard)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = getString(R.string.fruit_camera),
                                    fontFamily = PetitCochon,
                                    color = Color.White,
                                    fontSize = 30.sp,
                                    textAlign = TextAlign.Start
                                )
                                Text(
                                    text = getString(R.string.scan_fruit),
                                    fontFamily = PetitCochon,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Start
                                )
                            }
                            Image(
                                painter = painterResource(R.drawable.ic_camera),
                                contentDescription = "",
                                modifier = modifier
                                    .padding(15.dp)
                            )
                        }
                        Image(
                            painter = painterResource(R.drawable.apple),
                            contentDescription = "",
                            modifier = modifier
                                .padding(15.dp)
                        )
                    }
                }
            }
        }
        binding.cvListCard.setContent {
            CardComponent(
                borderColor = colorResource(R.color.red_secondary),
                cardColor = colorResource(R.color.red_primary),
                onClick = {
                    if (spLoaded) {
                        sp.play(soundId, 1f, 1f, 0, 0, 1f)
                    }
                    val intent = Intent(requireActivity(), FruitListActivity::class.java)
                    startActivity(intent)
                },
                enabled = true,
                modifier = modifier
                    .fillMaxWidth()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(SpacingStandard)
                ) {
                    Image(
                        painter = painterResource(R.drawable.banana),
                        contentDescription = stringResource(R.string.list_fruit),
                        modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(R.drawable.list),
                        contentDescription = stringResource(R.string.list_fruit),
                        modifier.weight(1f)
                    )
                }
            }
        }
        binding.cvGameCard.setContent {
            CardComponent(
                borderColor = colorResource(R.color.cream_secondary),
                cardColor = colorResource(R.color.cream_primary),
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = true,
                onClick = {
                    if (spLoaded) {
                        sp.play(soundId, 1f, 1f, 0, 0, 1f)
                    }
                    val intent = Intent(requireActivity(), GameActivity::class.java)
                    startActivity(intent)
                }

                ){
                Box(
                    modifier = modifier
                        .padding(SpacingStandard)
                        .fillMaxSize()
                ){
                    Image(
                        painter = painterResource(R.drawable.white_strawberry),
                        contentDescription = stringResource(R.string.game),
                        modifier = modifier.align(Alignment.Center)
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