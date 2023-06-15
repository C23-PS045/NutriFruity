package com.linggash.nutrifruity.ui.detail

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.data.Result
import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.databinding.ActivityFruitDetailBinding
import com.linggash.nutrifruity.ui.ViewModelFactory
import com.linggash.nutrifruity.ui.list.FruitListActivity

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FruitDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this, dataStore)
        val viewModel: FruitDetailViewModel by viewModels { factory }

        val fruit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(FruitListActivity.ID, Fruit::class.java)
        } else {
            intent.getParcelableExtra(FruitListActivity.ID)
        }
        val adapter = FruitBenefitAdapter()
        if (fruit != null) {
            binding.tvList.text = fruit.name
            Glide.with(this)
                .load(fruit.photoUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.imgDetailFruit)
            val cardColor = Color.parseColor(fruit.color.replace("0xFF", "#"))
            binding.actionBarDetail.setBackgroundColor(cardColor)
            binding.detailContent.setBackgroundColor(cardColor)
            viewModel.getFruitDetail(fruit.fruitId).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            val nutrition = result.data.nutrition.joinToString { it.nutrition }
                            binding.progressBar.visibility = View.GONE
                            binding.tvNutritionContent.text = nutrition
                            adapter.submitList(result.data.benefit)
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
            binding.rvBenefit.apply {
                layoutManager =  LinearLayoutManager(this@FruitDetailActivity)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }
}