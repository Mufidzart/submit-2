package com.mufidz.githubusersubmission2.local

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.githubusersubmission2.R

class UserLocalAdapter(private val listUserLocal: ArrayList<UserLocal>) :
    RecyclerView.Adapter<UserLocalAdapter.ListViewHolder>() {

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, company, location, repo, follower, following, photo) = listUserLocal[position]
        holder.tvName.text = name
        holder.tvCompany.text = company
        holder.tvRepo.text = repo + " Repositories"
        holder.imgPhoto.setImageResource(photo)
        holder.itemView.setOnClickListener {
            val user = UserLocal(
                name, company, location, repo, follower, following, photo
            )
            val intent = Intent(it.context, DetailLocal::class.java)
            intent.putExtra(DetailLocal.EXTRA_USER, user)
            it.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = (listUserLocal.size)

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.iv_user)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvCompany: TextView = itemView.findViewById(R.id.tv_company)
        var tvRepo: TextView = itemView.findViewById(R.id.tv_repository)
    }

}