package com.example.submissionfundamentalandi.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submissionfundamentalandi.data.database.UserGithubDao
import com.example.submissionfundamentalandi.data.database.UserRoomDatabase
import com.example.submissionfundamentalandi.data.response.UsersGithub
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUsersGithubDao: UserGithubDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUsersGithubDao = db.userGithubDao()
    }

    fun getAllUsers(): LiveData<List<UsersGithub>> = mUsersGithubDao.getAllUsers()

    fun insert(user: UsersGithub) {
        executorService.execute { mUsersGithubDao.insert(user) }
    }

    fun delete(user: UsersGithub) {
        executorService.execute { mUsersGithubDao.delete(user) }
    }

}