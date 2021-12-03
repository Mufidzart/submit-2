package com.mufidz.githubusersubmission2.local

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mufidz.githubusersubmission2.R

class DetailLocal : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_local)
        showLoading(true)

        val imgPhoto: ImageView = findViewById(R.id.img_user_detail)
        val tvNama: TextView = findViewById(R.id.tv_name_detail)
        val tvCompany: TextView = findViewById(R.id.tv_company_detail)
        val tvLocation: TextView = findViewById(R.id.tv_location)
        val tvRepository: TextView = findViewById(R.id.jml_repository)
        val tvFollower: TextView = findViewById(R.id.jml_follower)
        val tvFollowing: TextView = findViewById(R.id.jml_following)

        val user = intent.getParcelableExtra<UserLocal>(EXTRA_USER) as UserLocal
        setTitle("Detail User " + user.name)
        tvNama.text = user.name
        tvCompany.text = user.company
        tvLocation.text = user.location
        tvRepository.text = user.repo
        tvFollower.text = user.follower
        tvFollowing.text = user.following
        imgPhoto.setImageResource(user.photo)

        handler.postDelayed({
            showLoading(false)
        }, 3000)

    }

    private fun showLoading(state: Boolean) {
        progressBar = findViewById(R.id.progress_barLocal)
        progressBar.visibility = if (state) ProgressBar.VISIBLE else ProgressBar.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}