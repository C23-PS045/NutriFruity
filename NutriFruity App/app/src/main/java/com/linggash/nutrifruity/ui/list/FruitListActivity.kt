package com.linggash.nutrifruity.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.paging.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.linggash.nutrifruity.databinding.ActivityFruitListBinding
import com.linggash.nutrifruity.ui.adapter.LoadingStateAdapter
import com.linggash.nutrifruity.ui.detail.FruitDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class FruitListActivity : AppCompatActivity() {

    private val viewModel: FruitListViewModel by viewModels()

    private lateinit var binding: ActivityFruitListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        getData()
    }

    private fun setView() {
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvFruit.layoutManager = layoutManager
    }

    private fun getData() {
        val adapter = FruitListAdapter{
            val intent = Intent(this, FruitDetailActivity::class.java)
            intent.putExtra(ID, it.fruitId)
            startActivity(intent)
        }
        binding.rvFruit.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        viewModel.fruit().observe(this@FruitListActivity){
            adapter.submitData(lifecycle, it)
        }
    }

    companion object{
        const val ID = "fruitID"
    }
}