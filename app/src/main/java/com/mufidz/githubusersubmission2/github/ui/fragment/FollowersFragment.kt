package com.mufidz.githubusersubmission2.github.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufidz.githubusersubmission2.R
import com.mufidz.githubusersubmission2.databinding.FragmentFollowersBinding
import com.mufidz.githubusersubmission2.github.ui.GithubUserAdapter
import com.mufidz.githubusersubmission2.github.ui.detail.DetailUser

class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: GithubUserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUser.EXTRA_USERNAME).toString()
        _binding = FragmentFollowersBinding.bind(view)

        adapter = GithubUserAdapter()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) ProgressBar.VISIBLE else ProgressBar.GONE
    }
}