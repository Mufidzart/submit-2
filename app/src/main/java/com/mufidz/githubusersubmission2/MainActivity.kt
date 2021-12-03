package com.mufidz.githubusersubmission2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mufidz.githubusersubmission2.databinding.ActivityMainBinding
import com.mufidz.githubusersubmission2.github.menu.FavoriteActivity
import com.mufidz.githubusersubmission2.github.ui.GithubUserAdapter
import com.mufidz.githubusersubmission2.github.ui.MainViewModel
import com.mufidz.githubusersubmission2.local.UserLocal
import com.mufidz.githubusersubmission2.local.UserLocalAdapter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: GithubUserAdapter
    private lateinit var rvUser: RecyclerView
    private val listLocal = ArrayList<UserLocal>()
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        adapter = GithubUserAdapter()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        binding.apply {
            rvUser.adapter = adapter
            btnSearch.setOnClickListener {
                searchUser()
                if (etQuery.text.isNullOrEmpty()) Toast.makeText(
                    this@MainActivity,
                    "User tidak ditemukan",
                    Toast.LENGTH_SHORT
                ).show()
            }
            etQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    if (etQuery.text.isNullOrEmpty()) Toast.makeText(
                        this@MainActivity,
                        "User tidak ditemukan",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        viewModel.getSearchUser().observe(this, {
            if (it != null) {
                setListLocal(false)
                val adapter = GithubUserAdapter()
                adapter.setList(it)
                rvUser.adapter = adapter
                showLoading(false)
            }
        })
        searchUser()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.favorite -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }
    private fun searchUser() {
        binding.apply {
            val query = etQuery.text
            if (query.isNullOrEmpty()) {
                showLoading(true)
                showRecyclerlist()
                handler.postDelayed({
                    showLoading(false)
                }, 2000)
                return
            } else {
                showLoading(true)
                setListLocal(false)
                viewModel.setSearchUser(query.toString())
            }
        }
    }

    fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) ProgressBar.VISIBLE else ProgressBar.GONE
    }

    private val listUserLocal: ArrayList<UserLocal>
        get() {
            val dataName = resources.getStringArray(R.array.name)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepo = resources.getStringArray(R.array.repository)
            val dataFollower = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
            val listUserLocal = ArrayList<UserLocal>()
            for (i in dataName.indices) {
                val user = UserLocal(
                    dataName[i],
                    dataCompany[i],
                    dataLocation[i],
                    dataRepo[i],
                    dataFollower[i],
                    dataFollowing[i],
                    dataPhoto.getResourceId(i, -1)
                )
                listUserLocal.add(user)
            }
            return listUserLocal
        }

    private fun showRecyclerlist() {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = UserLocalAdapter(listLocal)
        rvUser.adapter = listUserAdapter
        setListLocal(true)
    }

    private fun setListLocal(state: Boolean) {
        if (state) {
            val listUserAdapter = UserLocalAdapter(listLocal)
            rvUser.adapter = listUserAdapter
            listLocal.addAll(listUserLocal)
        } else {
            listLocal.clear()
        }
    }
}