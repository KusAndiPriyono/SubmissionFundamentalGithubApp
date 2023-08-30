package com.example.submissionfundamentalandi.data.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionfundamentalandi.ui.detail.DetailViewModel
import com.example.submissionfundamentalandi.ui.favorite.FavoriteUserViewModel

class FavoriteUserViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteUserViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteUserViewModelFactory::class.java) {
                    INSTANCE = FavoriteUserViewModelFactory(application)
                }
            }
            return INSTANCE as FavoriteUserViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}