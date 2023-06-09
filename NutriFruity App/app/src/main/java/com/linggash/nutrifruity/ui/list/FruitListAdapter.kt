package com.linggash.nutrifruity.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.res.colorResource
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.databinding.FruitListItemBinding
import com.linggash.nutrifruity.ui.component.FruitItem

class FruitListAdapter(
    private val onClick: (Fruit) -> Unit,
) : PagingDataAdapter<Fruit, FruitListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FruitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class MyViewHolder(private val binding: FruitListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Fruit) {
            binding.root.setContent {
                FruitItem(
                    borderColor = colorResource(R.color.green_secondary),
                    cardColor = colorResource(R.color.green_primary),
                    name = data.name,
                    image = data.photoUrl,
                    onClick = {
                        onClick(data)
                    }
                )
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Fruit>() {
            override fun areItemsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Fruit, newItem: Fruit): Boolean {
                return oldItem.fruitId == newItem.fruitId
            }
        }
    }
}