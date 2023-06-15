package com.linggash.nutrifruity.ui.detail

import android.graphics.Color
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.data.Result
import com.linggash.nutrifruity.data.SettingPreferences
import com.linggash.nutrifruity.data.dataStore
import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.databinding.ActivityFruitDetailBinding
import com.linggash.nutrifruity.ui.ViewModelFactory
import com.linggash.nutrifruity.ui.list.FruitListActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class FruitDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitDetailBinding

    private lateinit var factory: ViewModelFactory
    private lateinit var viewModel: FruitDetailViewModel

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()

        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[FruitDetailViewModel::class.java]

        var pref : Boolean
        runBlocking {
            pref = SettingPreferences.getInstance(dataStore).getSoundSetting().first()
        }
        setView(pref)
    }

    private fun setView(isOn: Boolean) {
        val fruit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(FruitListActivity.ID, Fruit::class.java)
        } else {
            intent.getParcelableExtra(FruitListActivity.ID)
        }
        if (isOn){
            sp.setOnLoadCompleteListener{ _, _, status ->
                if (status == 0){
                    spLoaded = true
                }else {
                    Toast.makeText(this, "Gagal load", Toast.LENGTH_SHORT).show()
                }
            }
            soundId = sp.load(this, R.raw.back, 1)
        }else{
            spLoaded = false
        }
        val adapter = FruitBenefitAdapter()
        if (fruit != null) {
            binding.apply {
                binding.btnBack.setOnClickListener {
                    if (spLoaded) {
                        sp.play(soundId, 1f, 1f, 0, 0, 1f)
                    }
                    finish()
                }
                val cardColor = Color.parseColor(fruit.color.replace("0xFF", "#"))
                binding.root.setBackgroundColor(cardColor)
                tvList.text = fruit.name
                Glide.with(this@FruitDetailActivity)
                    .load(fruit.photoUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_gallery).error(R.drawable.ic_error))
                    .into(binding.imgDetailFruit)
                viewModel.getFruitDetail(fruit.fruitId).observe(this@FruitDetailActivity) { result ->
                    if (result != null) {
                        when (result) {
                            Result.Loading -> {
                                progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                val nutrition = result.data.nutrition.joinToString { it.nutrition }
                                progressBar.visibility = View.GONE
                                tvNutritionContent.text = nutrition
                                adapter.submitList(result.data.benefit)
                            }
                            is Result.Error -> {
                                progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this@FruitDetailActivity,
                                    "Tidak ada koneksi Internet",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                rvBenefit.apply {
                    layoutManager =  LinearLayoutManager(this@FruitDetailActivity)
                    setHasFixedSize(true)
                    this.adapter = adapter
                }
            }
        }
    }
}