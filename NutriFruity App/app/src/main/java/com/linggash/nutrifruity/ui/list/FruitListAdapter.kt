package com.linggash.nutrifruity.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.res.colorResource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.databinding.FruitListItemBinding
import com.linggash.nutrifruity.ui.component.FruitItem

class FruitListAdapter(
    private val onClick: (Fruit) -> Unit
) : ListAdapter<Fruit, FruitListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FruitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fruit = getItem(position)
        Log.d("Buah", fruit.toString())
        holder.bind(fruit)
    }

    inner class MyViewHolder(private val binding: FruitListItemBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(fruit: Fruit) {
            binding.root.setContent {
                FruitItem(
                    borderColor = colorResource(R.color.green_secondary),
                    cardColor = colorResource(R.color.green_primary),
                    name = fruit.name,
                    image = fruit.photoUrl,
                    onClick = {
                        onClick(fruit)
                    }
                )
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Fruit> =
            object : DiffUtil.ItemCallback<Fruit>() {
                override fun areItemsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
                    return oldItem.fruitId == newItem.fruitId
                }
                override fun areContentsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
                    return oldItem == newItem
                }
            }
    }
}