package com.example.submissionfundamentalandi.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionfundamentalandi.BuildConfig
import com.example.submissionfundamentalandi.data.repository.UserRepository
import com.example.submissionfundamentalandi.data.response.DetailUser
import com.example.submissionfundamentalandi.data.response.UsersGithub
import com.example.submissionfundamentalandi.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val _detailUser = MutableLiveData<DetailUser>()
    val detailUser: LiveData<DetailUser> = _detailUser

    private val _followers = MutableLiveData<List<UsersGithub>>()
    val followers: LiveData<List<UsersGithub>> = _followers

    private val _followings = MutableLiveData<List<UsersGithub>>()
    val followings: LiveData<List<UsersGithub>> = _followings

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFollowers = MutableLiveData<Boolean>()
    val isLoadingFollowers: LiveData<Boolean> = _isLoadingFollowers

    private val _isLoadingFollowings = MutableLiveData<Boolean>()
    val isLoadingFollowings: LiveData<Boolean> = _isLoadingFollowings

    private val mUserRepository: UserRepository = UserRepository(application)
    fun getAllUsers(): LiveData<List<UsersGithub>> = mUserRepository.getAllUsers()
    fun insert(user: UsersGithub) {
        mUserRepository.insert(user)
    }

    fun delete(user: UsersGithub) {
        mUserRepository.delete(user)
    }

    internal fun getDetailUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(login, BuildConfig.KEY)
        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    _detailUser.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    internal fun getDetailFollowers(login: String) {
        _isLoadingFollowers.value = true
        val client = ApiConfig.getApiService().getFollowers(login, BuildConfig.KEY)
        client.enqueue(object : Callback<List<UsersGithub>> {
            override fun onResponse(
                call: Call<List<UsersGithub>>,
                response: Response<List<UsersGithub>>
            ) {
                _isLoadingFollowers.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    _followers.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UsersGithub>>, t: Throwable) {
                _isLoadingFollowers.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    internal fun getDetailFollowing(login: String) {
        _isLoadingFollowings.value = true
        val client = ApiConfig.getApiService().getFollowing(login, BuildConfig.KEY)
        client.enqueue(object : Callback<List<UsersGithub>> {
            override fun onResponse(
                call: Call<List<UsersGithub>>,
                response: Response<List<UsersGithub>>
            ) {
                _isLoadingFollowings.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    _followings.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UsersGithub>>, t: Throwable) {
                _isLoadingFollowings.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}