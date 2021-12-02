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

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<UserGitHub>>()

    fun setListFollowers(usernama: String) {
        RetrofitClient.apiInstance
            .getFollowers(usernama)
            .enqueue(object : Callback<ArrayList<UserGitHub>> {
                override fun onResponse(
                    call: Call<ArrayList<UserGitHub>>,
                    response: Response<ArrayList<UserGitHub>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserGitHub>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<UserGitHub>> {
        return listFollowers
    }
}