package com.mufidz.githubusersubmission2.github.ui.favorite

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mufidz.githubusersubmission2.R
import com.mufidz.githubusersubmission2.databinding.ItemUserBinding
import com.mufidz.githubusersubmission2.github.model.Favorite

class FavoriteAdapter (private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listFavorites = ArrayList<Favorite>()
    set(listFavorites) {
        if (listFavorites.size > 0){
            this.listFavorites.clear()
        }
        this.listFavorites.addAll(listFavorites)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int = this.listFavorites.size

    inner class FavoriteViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.apply {
                tvName.text = favorite.username
                viewCard.setOnClickListener {
                    onItemClickCallback.onItemClicked(favorite, adapterPosition)
                }
            }
        }
    }

    fun addItem(note: Favorite) {
        this.listFavorites.add(note)
        notifyItemInserted(this.listFavorites.size - 1)
    }
    fun removeItem(position: Int) {
        this.listFavorites.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavorites.size)
    }

    interface OnItemClickCallback {
        fun onItemClicked(selectedFavorite: Favorite?, position: Int)
    }
}