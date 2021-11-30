package com.mufidz.githubusersubmission2.github.ui.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mufidz.githubusersubmission2.github.api.RetrofitClient
import com.mufidz.githubusersubmission2.github.model.UserGitHub
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<UserGitHub>>()

    fun setListFollowing(usernama: String) {
        RetrofitClient.apiInstance
            .getFollowing(usernama)
            .enqueue(object : Callback<ArrayList<UserGitHub>> {
                override fun onResponse(
                    call: Call<ArrayList<UserGitHub>>,
                    response: Response<ArrayList<UserGitHub>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserGitHub>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<UserGitHub>> {
        return listFollowing
    }
}