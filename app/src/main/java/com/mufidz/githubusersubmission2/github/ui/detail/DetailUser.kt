package com.mufidz.githubusersubmission2.github.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mufidz.githubusersubmission2.MainActivity
import com.mufidz.githubusersubmission2.R
import com.mufidz.githubusersubmission2.databinding.ActivityDetailUserBinding

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(false)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail().observe(this,{
            if (it != null){
                binding.apply {
                    tvNameDetail.text = it.name
                    tvUsername.text = it.login
                    jmlFollower.text = it.followers.toString()
                    jmlFollowing.text = it.following.toString()
                    jmlRepository.text = it.public_repos.toString()
                    Glide.with(this@DetailUser)
                        .load(it.avatar_url)
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
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) ProgressBar.VISIBLE else ProgressBar.GONE
    }

    companion object{
        const val EXTRA_USERNAME = "extra_username"
    }
}