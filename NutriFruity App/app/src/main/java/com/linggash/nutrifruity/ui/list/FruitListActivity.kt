package com.linggash.nutrifruity.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.linggash.nutrifruity.data.Result
import com.linggash.nutrifruity.databinding.ActivityFruitListBinding
import com.linggash.nutrifruity.ui.detail.FruitDetailActivity

class FruitListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFruitListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: FruitListViewModel by viewModels { factory }

        val fruitAdapter = FruitListAdapter{
            val intent = Intent(this, FruitDetailActivity::class.java)
            intent.putExtra(ID, it.fruitId)
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
                            "Terjadi kesalahan" + result.error,
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