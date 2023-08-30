package com.example.submissionfundamentalandi.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.submissionfundamentalandi.data.response.UsersGithub

@Dao
interface UserGithubDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UsersGithub)

    @Delete
    fun delete(user: UsersGithub)

    @Query("SELECT * from UsersGithub ORDER BY login ASC")
    fun getAllUsers(): LiveData<List<UsersGithub>>
}