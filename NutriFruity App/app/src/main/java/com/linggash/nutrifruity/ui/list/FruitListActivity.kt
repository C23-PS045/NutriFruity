package com.linggash.nutrifruity.ui.list

import android.content.Intent
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.GridLayoutManager
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.data.Result
import com.linggash.nutrifruity.databinding.ActivityFruitListBinding
import com.linggash.nutrifruity.ui.ViewModelFactory
import com.linggash.nutrifruity.ui.detail.FruitDetailActivity
import com.linggash.nutrifruity.ui.theme.PetitCochon
import com.linggash.nutrifruity.ui.theme.SpacingLarge

class FruitListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitListBinding

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()
        sp.setOnLoadCompleteListener{ _, _, status ->
            if (status == 0){
                spLoaded = true
            }else {
                Toast.makeText(this, "Gagal load", Toast.LENGTH_SHORT).show()
            }
        }
        soundId = sp.load(this, R.raw.btn, 1)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: FruitListViewModel by viewModels { factory }

        val fruitAdapter = FruitListAdapter{
            if (spLoaded) {
                sp.play(soundId, 1f, 1f, 0, 0, 1f)
            }
            val intent = Intent(this, FruitDetailActivity::class.java)
            intent.putExtra(ID, it)
            startActivity(intent)
        }

        binding.appBarLayout.setContent {
            val modifier = Modifier
            val cardColor = colorResource(R.color.orange_primary)
            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor, disabledContainerColor = cardColor),
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                modifier = modifier.
                        fillMaxWidth()
            ) {
                Box(
                    modifier = modifier
                        .padding(horizontal = 5.dp)
                        .fillMaxWidth()
                ) {
                    IconButton(
                        modifier = modifier.align(Alignment.CenterStart),
                        onClick = {
                            finish()
                        }
                    ) {
                        Icon(
                            tint = Color.White,
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                    Text(
                        text = stringResource(R.string.list_fruit),
                        color = Color.White,
                        fontFamily = PetitCochon,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                }
            }
        }
        viewModel.getFruit().observe(this) { result ->
            if (result != null) {
                when (result) {
                    Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val fruitData = result.data
                        fruitAdapter.submitList(fruitData)
                        Log.d("jalan", "sukses ${fruitData}")
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Tidak ada koneksi Internet",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        binding.rvFruit.apply {
            layoutManager = GridLayoutManager(this@FruitListActivity, 2)
            setHasFixedSize(true)
            adapter = fruitAdapter
        }
    }

    companion object{
        const val ID = "fruitID"
    }
}