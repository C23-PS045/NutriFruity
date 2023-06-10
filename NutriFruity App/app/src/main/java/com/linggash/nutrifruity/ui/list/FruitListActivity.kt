package com.linggash.nutrifruity.ui.list

import android.content.Intent
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.data.Result
import com.linggash.nutrifruity.databinding.ActivityFruitListBinding
import com.linggash.nutrifruity.ui.ViewModelFactory
import com.linggash.nutrifruity.ui.detail.FruitDetailActivity

class FruitListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitListBinding

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

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