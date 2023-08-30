package com.example.submissionfundamentalandi.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamentalandi.R
import com.example.submissionfundamentalandi.data.helper.FavoriteUserViewModelFactory
import com.example.submissionfundamentalandi.data.response.UsersGithub
import com.example.submissionfundamentalandi.databinding.ActivityFavoriteUserBinding
import com.example.submissionfundamentalandi.ui.detail.DetailUsersActivity
import com.example.submissionfundamentalandi.ui.main.UserAdapter

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    private var _activityFavoriteUserBinding: ActivityFavoriteUserBinding? = null
    private val binding get() = _activityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoriteUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.rvFavoriteUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavoriteUser?.setHasFixedSize(true)

        favoriteUserViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteUserViewModel.getAllUsers().observe(this) { listUser ->
            if (listUser.isNotEmpty()) {
                setListData(listUser)
            } else {
                binding?.rvFavoriteUser?.visibility = View.GONE
                binding?.tvFavoriteUserNoneData?.visibility = View.VISIBLE
            }
        }

    }

    private fun setListData(listUser: List<UsersGithub>) {
        val adapter = UserAdapter(listUser)
        binding?.rvFavoriteUser?.adapter = adapter

        adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: UsersGithub) {
                sendSelectedUser(data)
            }

        })
    }

    private fun sendSelectedUser(data: UsersGithub) {
        val intent = Intent(this, DetailUsersActivity::class.java)
        intent.putExtra(DetailUsersActivity.EXTRA_USERS, data)
        startActivity(intent)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = FavoriteUserViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}