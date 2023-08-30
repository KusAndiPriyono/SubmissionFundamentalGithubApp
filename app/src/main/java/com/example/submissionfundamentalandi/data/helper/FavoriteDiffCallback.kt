package com.example.submissionfundamentalandi.data.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.submissionfundamentalandi.data.response.UsersGithub

class FavoriteDiffCallback(
    private val oldFavoriteList: List<UsersGithub>,
    private val newFavoriteList: List<UsersGithub>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size
    override fun getNewListSize(): Int = newFavoriteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].login == newFavoriteList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = oldFavoriteList[oldItemPosition]
        val newFavorite = newFavoriteList[newItemPosition]
        return oldFavorite.login == newFavorite.login &&
                oldFavorite.avatarUrl == newFavorite.avatarUrl
    }
}