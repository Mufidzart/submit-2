package com.mufidz.githubusersubmission2.github.ui.detail

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mufidz.githubusersubmission2.R
import com.mufidz.githubusersubmission2.databinding.ActivityDetailUserBinding
import com.mufidz.githubusersubmission2.github.db.DatabaseContract
import com.mufidz.githubusersubmission2.github.db.FavoriteHelper
import com.mufidz.githubusersubmission2.github.model.Favorite
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

const val ISOFORMAT = "yyyy/MM/dd'T'HH:mm:ss"

class DetailUser : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var favoriteHelper: FavoriteHelper
    private val handler = Handler(Looper.getMainLooper())
    private var favorite: Favorite? = null
    private var position: Int = 0
    private var isFavorite = false
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
            setTitle("Detail User " + it.name)
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
        handler.postDelayed({
            showLoading(false)
        }, 3000)

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        favorite = intent.getParcelableExtra(EXTRA_FAVORITE)
        if (favorite != null) {
            isFavorite = true
        } else {
            favorite = Favorite()
        }
        val btnTitle: String
        if (isFavorite){
            btnTitle = "Unfavorite"
            favorite.let {
                binding.btnAddFavorite.text = btnTitle
            }
        }
        binding.btnAddFavorite.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id != R.id.btn_add_favorite) return

        val username = binding.tvUsername.text.toString().trim()
        if (username.isEmpty()) {
            binding.tvUsername.error = "Can not get username!"
            return
        }
        favorite?.username = username

        val values = ContentValues()
        val dateFormat = SimpleDateFormat(ISOFORMAT);
        val date = dateFormat.format(Date());
        values.put(DatabaseContract.FavoriteColumns.USERNAME, username)
        values.put(DatabaseContract.FavoriteColumns.DATE, date);

        if (isFavorite){
            Log.d("DEBUG","You Are my favorite",)

            val result = favoriteHelper.deleteByUsername(favorite?.username.toString(), values).toLong()
            if (result > 0) {
                val btnTitle = "Favorite"
                favorite.let {
                    binding.btnAddFavorite.text = btnTitle
                }
                finish()
            } else {
                Toast.makeText(this, "Gagal membatalkan favorite", Toast.LENGTH_SHORT).show()
            }

            return
        }
        Log.d("DEBUG","You Are not my favorite")
        Log.d("DEBUG", values.toString())

        favorite?.date = getCurrentDate()
        values.put(DatabaseContract.FavoriteColumns.DATE, getCurrentDate())
        val result = favoriteHelper.insert(values)
        if (result > 0){
            val btnTitle = "Unfavorite"
            favorite.let {
                binding.btnAddFavorite.text = btnTitle
            }
            val data =
            Log.e("data", "dbResponse: " )
            Toast.makeText(this, "Sukse menambah data", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentDate(): String? {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) ProgressBar.VISIBLE else ProgressBar.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_FAVORITE = "extra_favorite"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}