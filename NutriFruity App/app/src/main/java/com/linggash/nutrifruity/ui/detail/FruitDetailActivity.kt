package com.linggash.nutrifruity.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.linggash.nutrifruity.data.Result
import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.databinding.ActivityFruitDetailBinding
import com.linggash.nutrifruity.ui.ViewModelFactory
import com.linggash.nutrifruity.ui.list.FruitListActivity

class FruitDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: FruitDetailViewModel by viewModels { factory }

        val fruit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(FruitListActivity.ID, Fruit::class.java)
        } else {
            intent.getParcelableExtra(FruitListActivity.ID)
        }

        binding.detailContent.setContent {
            if (fruit != null) {
                FruitDetailContent(name = fruit.name, imageUrl = fruit.photoUrl)
            }
        }

        if (fruit != null) {
            viewModel.getFruitDetail(fruit.fruitId).observe(this){ result ->
                if (result != null) {
                    when (result) {
                        Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            Log.d("Sukses", result.data.toString())
                            binding.progressBar.visibility = View.GONE
                            binding.detailContent.setContent {
                                FruitDetailContent(
                                    name = result.data.fruit.name,
                                    imageUrl = result.data.fruit.photoUrl,
                                    nutrition = result.data.nutrition,
                                    benefit = result.data.benefit,
                                )
                            }
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
        }
    }
}