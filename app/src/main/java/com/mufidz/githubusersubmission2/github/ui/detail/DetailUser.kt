package com.mufidz.githubusersubmission2.github.ui.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mufidz.githubusersubmission2.R
import com.mufidz.githubusersubmission2.databinding.ActivityDetailUserBinding

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)
        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                val office: String
                val place: String
                office = it.company ?: "Unknown Company"
                place = it.company ?: "Unknown Location"
                binding.apply {
                    tvNameDetail.text = it.name
                    tvUsername.text = it.login
                    tvCompanyDetail.text = office
                    tvLocation.text = place
                    jmlFollower.text = it.followers.toString()
                    jmlFollowing.text = it.following.toString()
                    jmlRepository.text = it.publicRepos.toString()
                    Glide.with(this@DetailUser)
                        .load(it.avatarUrl)
                        .error(R.drawable.default_avatar)
                        .fitCenter()
                        .into(imgUserDetail)
                }
            }
        })
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
        handler.postDelayed({
            showLoading(false)
        }, 3000)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) ProgressBar.VISIBLE else ProgressBar.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}