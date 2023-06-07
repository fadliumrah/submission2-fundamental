package com.fadli.submission2.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadli.submission2.data.DataUsers
import com.fadli.submission2.databinding.ItemUserBinding
import com.fadli.submission2.ui.detailUser.DetailUser
import com.fadli.submission2.util.Constanta.EXTRA_USER
import com.fadli.submission2.util.Resource

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private val listDataUsers = ArrayList<DataUsers>()

    inner class UserViewHolder(private val view: ItemUserBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(dataUsers: DataUsers){
            view.apply {
                userName.text = dataUsers.username
            }
            Glide.with(itemView.context)
                .load(dataUsers.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(view.avatars)

            itemView.setOnClickListener {
                val i = Intent(itemView.context, DetailUser::class.java)
                i.putExtra(EXTRA_USER,dataUsers.username)
                itemView.context.startActivity(i)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setAllData(data: List<DataUsers>){
        listDataUsers.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listDataUsers[position])
    }

    override fun getItemCount(): Int = listDataUsers.size
}