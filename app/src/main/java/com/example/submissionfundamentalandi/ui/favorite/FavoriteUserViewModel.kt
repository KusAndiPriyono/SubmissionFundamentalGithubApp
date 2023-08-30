package com.example.submissionfundamentalandi.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionfundamentalandi.data.repository.UserRepository
import com.example.submissionfundamentalandi.data.response.UsersGithub

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUsers(): LiveData<List<UsersGithub>> = mUserRepository.getAllUsers()
}