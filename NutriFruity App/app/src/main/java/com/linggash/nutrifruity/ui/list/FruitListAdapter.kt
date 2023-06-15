package com.linggash.nutrifruity.ui.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.databinding.FruitListItemBinding


class FruitListAdapter(
    private val onClick: (Fruit) -> Unit
) : ListAdapter<Fruit, FruitListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FruitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fruit = getItem(position)
        holder.bind(fruit)
    }

    inner class MyViewHolder(private val binding: FruitListItemBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(fruit: Fruit) {
            binding.tvFruitListItem.text = fruit.name
            Glide.with(itemView.context)
                .load(fruit.photoUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.ivListItem)
            val borderColor = Color.parseColor(fruit.borderColor.replace("0xFF", "#"))
            val cardColor = Color.parseColor(fruit.color.replace("0xFF", "#"))
            binding.cvListItem.setCardBackgroundColor(borderColor)
            binding.idListItem.setCardBackgroundColor(cardColor)
            itemView.setOnClickListener {
                onClick(fruit)
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