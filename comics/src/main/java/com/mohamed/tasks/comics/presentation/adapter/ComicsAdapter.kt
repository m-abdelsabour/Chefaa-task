package com.mohamed.tasks.comics.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mohamed.tasks.comics.R
import com.mohamed.tasks.comics.databinding.ItemComicBinding
import com.mohamed.tasks.comics.domain.model.Comics
import com.squareup.picasso.Picasso

class ComicsAdapter(private val itemClick: (Comics) -> Unit,private val itemLongClick: (Comics) -> Unit) :
    ListAdapter<Comics, ComicsAdapter.MealViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            ItemComicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { itemClick(item) }
        holder.itemView.setOnLongClickListener {
            itemLongClick(item)
            return@setOnLongClickListener true
        }
    }

    class MealViewHolder(private val binding: ItemComicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Comics) = with(binding) {
            item.textObjects?.firstOrNull()?.text?.let {
                if (it.length >= 80) {
                    tvCaption.text="${it.substring(0, 80)}..."
                } else {
                    tvCaption.text=it
                }
            }?:kotlin.run {
                tvCaption.text = buildString { append("No Caption") }
            }
            item.images?.firstOrNull()?.let {
                val url="${it.path}.${it.extension}"
                Picasso.get()
                    .load(url)
                    .fit().into(ivPoster)
            }?:kotlin.run {
                ivPoster.setImageResource(0)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Comics>() {
        override fun areItemsTheSame(oldItem: Comics, newItem: Comics): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Comics, newItem: Comics
        ): Boolean {
            return oldItem == newItem
        }
    }
}