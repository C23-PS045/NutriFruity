package com.linggash.nutrifruity.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.linggash.nutrifruity.databinding.ActivityFruitDetailBinding
import com.linggash.nutrifruity.ui.list.FruitListActivity

class FruitDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitDetailBinding
    private val viewModel : FruitDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getLongExtra(FruitListActivity.ID, -1L)
        viewModel.getFruitDetail(id)
        viewModel.fruitDetail.observe(this){
            binding.root.setContent {
                FruitDetailContent(
                    name = it.name,
                    imageUrl = it.photoUrl,
                    nutrition = it.nutritionResponse,
                    benefit = it.benefit,
                )
            }
        }

    }
}