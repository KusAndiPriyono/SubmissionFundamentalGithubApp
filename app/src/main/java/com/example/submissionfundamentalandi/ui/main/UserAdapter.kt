package com.example.submissionfundamentalandi.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionfundamentalandi.data.response.UsersGithub
import com.example.submissionfundamentalandi.databinding.ItemUserBinding


class UserAdapter(private val listUsers: List<UsersGithub>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setOnItemClickCallBack(callback: OnItemClickCallBack) {
        onItemClickCallBack = callback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUsers[position]
        holder.binding.tvUserName.text = user.login
        Glide.with(holder.binding.ivItem.context)
            .load(user.avatarUrl)
            .into(holder.binding.ivItem)

        holder.itemView.setOnClickListener {
            onItemClickCallBack?.onItemClicked(user)
        }
    }


    override fun getItemCount(): Int = listUsers.size

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallBack {
        fun onItemClicked(data: UsersGithub)
    }
}