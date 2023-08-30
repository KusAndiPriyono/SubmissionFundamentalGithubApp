package com.example.submissionfundamentalandi.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submissionfundamentalandi.R
import com.example.submissionfundamentalandi.data.helper.FavoriteUserViewModelFactory
import com.example.submissionfundamentalandi.data.response.DetailUser
import com.example.submissionfundamentalandi.data.response.UsersGithub
import com.example.submissionfundamentalandi.databinding.ActivityDetailUsersBinding
import com.example.submissionfundamentalandi.ui.adapter.SectionsPagerAdapter
import com.example.submissionfundamentalandi.ui.favorite.FavoriteUserActivity
import com.example.submissionfundamentalandi.ui.settings.DarkModeActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUsersBinding
    private lateinit var detailViewModel: DetailViewModel

    private var buttonFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabsLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        detailViewModel = obtainViewModel(this)

        val usersIntent = intent.getParcelableExtra<UsersGithub>(EXTRA_USERS) as UsersGithub
        detailViewModel.getDetailUser(usersIntent.login)
        detailViewModel.getDetailFollowers(usersIntent.login)
        detailViewModel.getDetailFollowing(usersIntent.login)

        detailViewModel.detailUser.observe(this) {
            setDetailUser(it)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.getAllUsers().observe(this) {
            buttonFavorite = it.contains(usersIntent)
            if (buttonFavorite) {
                binding.fabLikeFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_favorite_red_24
                    )
                )
            } else {
                binding.fabLikeFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_favorite_border_24
                    )
                )
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fabLikeFavorite.setOnClickListener {
            if (buttonFavorite) {
                detailViewModel.delete(usersIntent)
                Snackbar.make(
                    binding.root,
                    StringBuilder(usersIntent.login + " ").append(resources.getString(R.string.delete_success)),
                    Snackbar.LENGTH_LONG
                ).setAction(resources.getString(R.string.undo)) {
                    detailViewModel.insert(usersIntent)
                    Toast.makeText(this, resources.getString(R.string.undo), Toast.LENGTH_LONG)
                        .show()
                }.show()
            } else {
                detailViewModel.insert(usersIntent)
                Snackbar.make(
                    binding.root,
                    StringBuilder(usersIntent.login + " ").append(resources.getString(R.string.added_success)),
                    Snackbar.LENGTH_LONG
                ).setAction(resources.getString(R.string.data_favorite)) {
                    startActivity(Intent(this, FavoriteUserActivity::class.java))
                }.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            R.id.favorite -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.settings -> {
                val intent = Intent(this, DarkModeActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = FavoriteUserViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setDetailUser(detailUser: DetailUser) {
        Glide.with(this)
            .load(detailUser.avatarUrl)
            .into(binding.ivDetailImage)
        binding.tvDetailUserName.text = detailUser.login.toUserNameFormat()
        binding.tvDetailName.text = detailUser.name
        binding.tvFollowersCount.text = detailUser.followers
        binding.tvFollowingCount.text = detailUser.following
        binding.tvReposCount.text = detailUser.publicRepos
    }

    companion object {
        const val EXTRA_USERS = "extra_users"


        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2

        )
    }
}

private fun String.toUserNameFormat(): String {
    return StringBuilder("@").append(this).toString()
}
