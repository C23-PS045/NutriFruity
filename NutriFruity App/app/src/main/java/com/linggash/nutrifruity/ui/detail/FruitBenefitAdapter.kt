package com.linggash.nutrifruity.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linggash.nutrifruity.database.Benefit
import com.linggash.nutrifruity.databinding.BenefitListItemBinding

class FruitBenefitAdapter : ListAdapter<Benefit, FruitBenefitAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = BenefitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val benefit = getItem(position)
        holder.bind(benefit)
    }

    inner class MyViewHolder(private val binding: BenefitListItemBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(benefit: Benefit) {
            binding.tvBullet.text = Typography.bullet.toString()
            binding.tvBenefitList.text = benefit.benefit
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Benefit> =
            object : DiffUtil.ItemCallback<Benefit>() {
                override fun areItemsTheSame(oldItem: Benefit, newItem: Benefit): Boolean {
                    return oldItem.benId == newItem.benId
                }
                override fun areContentsTheSame(oldItem: Benefit, newItem: Benefit): Boolean {
                    return oldItem == newItem
                }
            }
    }
}