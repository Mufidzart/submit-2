package com.mufidz.githubusersubmission2

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufidz.githubusersubmission2.databinding.ActivityMainBinding
import com.mufidz.githubusersubmission2.github.model.UserGitHub
import com.mufidz.githubusersubmission2.github.ui.detail.DetailUser
import com.mufidz.githubusersubmission2.github.ui.GithubUserAdapter
import com.mufidz.githubusersubmission2.github.ui.MainViewModel
import java.nio.file.Files.find

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: GithubUserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = GithubUserAdapter()
        adapter.setOnItemClickCallback(object : GithubUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserGitHub) {
                showLoading(true)
                Intent(this@MainActivity, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

//            btnSearch.setOnClickListener {
//                searchUser()
//            }

            etQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        viewModel.getSearchUser().observe(this,{
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            } else {

            }
        })
    }
    private fun searchUser() {
        binding.apply {
            val query = etQuery.text
            if (query.isNullOrEmpty()) return
            showLoading(true)
            viewModel.setSearchUser(query.toString())
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) ProgressBar.VISIBLE else ProgressBar.GONE
    }
}