package com.example.submissionfundamentalandi.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submissionfundamentalandi.BuildConfig
import com.example.submissionfundamentalandi.data.response.SearchUsers
import com.example.submissionfundamentalandi.data.response.UsersGithub
import com.example.submissionfundamentalandi.data.retrofit.ApiConfig
import com.example.submissionfundamentalandi.ui.settings.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val pref: SettingPreferences) : ViewModel() {

    private val _user = MutableLiveData<List<UsersGithub>?>()
    val user: MutableLiveData<List<UsersGithub>?> = _user

    private val _listUser = MutableLiveData<List<UsersGithub>>()
    val listUser: LiveData<List<UsersGithub>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        getData()
    }

    private fun getData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(BuildConfig.KEY)
        client.enqueue(object : Callback<List<UsersGithub>> {
            override fun onResponse(
                call: Call<List<UsersGithub>>,
                response: Response<List<UsersGithub>>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UsersGithub>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getDataSearch(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUser(query, BuildConfig.KEY)
        client.enqueue(object : Callback<SearchUsers> {
            override fun onResponse(call: Call<SearchUsers>, response: Response<SearchUsers>) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUsers>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    companion object {
        private const val TAG = "UserViewModel"
    }
}