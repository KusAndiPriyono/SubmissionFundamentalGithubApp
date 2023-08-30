package com.example.submissionfundamentalandi.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamentalandi.R
import com.example.submissionfundamentalandi.data.response.UsersGithub
import com.example.submissionfundamentalandi.databinding.ActivityMainBinding
import com.example.submissionfundamentalandi.ui.detail.DetailUsersActivity
import com.example.submissionfundamentalandi.ui.favorite.FavoriteUserActivity
import com.example.submissionfundamentalandi.ui.settings.DarkModeActivity
import com.example.submissionfundamentalandi.ui.settings.SettingPreferences
import com.example.submissionfundamentalandi.ui.settings.SettingViewModelFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    private var listUser = listOf<UsersGithub>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User"

        val pref = SettingPreferences.getInstance(dataStore)
        userViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pref)
        )[UserViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchUserName()

        userViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.searchView.queryHint = "Search User"

        userViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        userViewModel.user.observe(this) { user ->
            if (user != null) {
                listUser = user
            }
            if (user != null) {
                setListData(user)
            }
        }

        userViewModel.listUser.observe(this@MainActivity) { listUser ->
            setListData(listUser)
        }

    }


    private fun searchUserName() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (query != null) {
                    userViewModel.getDataSearch(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    userViewModel.user.value = listUser
                }
                return true
            }
        })
    }


    private fun setListData(githubList: List<UsersGithub>) {
        adapter = UserAdapter(githubList)
        binding.rvUser.adapter = adapter
        adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: UsersGithub) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: UsersGithub) {
        val moveIntent = Intent(this@MainActivity, DetailUsersActivity::class.java)
        moveIntent.putExtra(DetailUsersActivity.EXTRA_USERS, data)
        startActivity(moveIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.settings -> {
                val intent = Intent(this, DarkModeActivity::class.java)
                startActivity(intent)
                true
            }

            else -> true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}


