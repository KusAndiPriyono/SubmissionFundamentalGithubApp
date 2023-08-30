package com.example.submissionfundamentalandi.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamentalandi.R
import com.example.submissionfundamentalandi.data.response.UsersGithub
import com.example.submissionfundamentalandi.databinding.FragmentFollowBinding
import com.example.submissionfundamentalandi.ui.detail.DetailUsersActivity
import com.example.submissionfundamentalandi.ui.detail.DetailViewModel
import com.example.submissionfundamentalandi.ui.main.UserAdapter


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailViewModel = ViewModelProvider(
            requireActivity()
        )[DetailViewModel::class.java]

        var position = 0
        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
        }
        if (position == 1) {
            binding.sectionLabel.text = getString(R.string.followers)
            detailViewModel.followers.observe(viewLifecycleOwner) {
                setListDataFollow(it)
            }
            detailViewModel.isLoadingFollowers.observe(viewLifecycleOwner) {
                binding.progressBarFollow.visibility = if (it) View.VISIBLE else View.GONE
            }

        } else {
            binding.sectionLabel.text = getString(R.string.following)
            detailViewModel.followings.observe(viewLifecycleOwner) {
                setListDataFollow(it)
            }
            detailViewModel.isLoadingFollowings.observe(viewLifecycleOwner) {
                binding.progressBarFollow.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

    }

    private fun setListDataFollow(user: List<UsersGithub>) {
        binding.sectionLabel.visibility = if (user.isEmpty()) View.VISIBLE else View.GONE
        binding.rvFollow.apply {
            binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
            val listUserAdapter = UserAdapter(user)
            binding.rvFollow.adapter = listUserAdapter

            listUserAdapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack {
                override fun onItemClicked(data: UsersGithub) {
                    val intent = Intent(context, DetailUsersActivity::class.java)
                    intent.putExtra(DetailUsersActivity.EXTRA_USERS, data)
                    startActivity(intent)
                    activity?.finish()
                }

            })
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}