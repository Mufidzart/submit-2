package com.mufidz.githubusersubmission2.local

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.mufidz.githubusersubmission2.R

class DtailLocal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dtail_local)

        val imgPhoto: ImageView = findViewById(R.id.img_user_detail)
        val tvNama: TextView = findViewById(R.id.tv_name_detail)
        val tvCompany: TextView = findViewById(R.id.tv_company_detail)
        val tvLocation: TextView = findViewById(R.id.tv_location)
        val tvRepository: TextView = findViewById(R.id.jml_repository)
        val tvFollower: TextView = findViewById(R.id.jml_follower)
        val tvFollowing: TextView = findViewById(R.id.jml_following)

        val user = intent.getParcelableExtra<UserLocal>(EXTRA_USER) as UserLocal
        tvNama.text = user.name
        tvCompany.text = user.company
        tvLocation.text = user.location
        tvRepository.text = user.repo
        tvFollower.text = user.follower
        tvFollowing.text = user.following
        imgPhoto.setImageResource(user.photo)

    }
    companion object {
        const val EXTRA_USER = "extra_user"
    }
}