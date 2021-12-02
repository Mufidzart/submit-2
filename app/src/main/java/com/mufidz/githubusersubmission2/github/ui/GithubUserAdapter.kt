package com.mufidz.githubusersubmission2.github.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mufidz.githubusersubmission2.R
import com.mufidz.githubusersubmission2.databinding.ActivityMainBinding
import com.mufidz.githubusersubmission2.databinding.ItemUserBinding
import com.mufidz.githubusersubmission2.github.model.UserGitHub
import com.mufidz.githubusersubmission2.github.ui.detail.DetailUser

class GithubUserAdapter : RecyclerView.Adapter<GithubUserAdapter.UserViewHolder>() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar
    private val list = ArrayList<UserGitHub>()

    fun setList(users: ArrayList<UserGitHub>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserGitHub) {
            binding.root.setOnClickListener {
                val intent = Intent(it.context, DetailUser::class.java)
                intent.putExtra(DetailUser.EXTRA_USERNAME, user.login)
                it.context.startActivity(intent)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .error(R.drawable.default_avatar)
                    .centerCrop()
                    .into(ivUser)
                tvName.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
