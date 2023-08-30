package com.example.submissionfundamentalandi.data.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchUsers(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<UsersGithub>
)

@Entity
@Parcelize
data class UsersGithub(

    @PrimaryKey
    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    val login: String,

    @ColumnInfo(name = "avatarUrl")
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    ) : Parcelable

data class DetailUser(
    var login: String,
    var name: String,

    @field:SerializedName("avatar_url")
    var avatarUrl: String? = null,

    @field:SerializedName("public_repos")
    var publicRepos: String,
    var followers: String,
    var following: String
)
