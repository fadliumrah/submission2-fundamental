package com.fadli.submission2.ui.detailUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadli.submission2.R
import com.fadli.submission2.data.DataUsers
import com.fadli.submission2.databinding.ActivityDetailUserBinding
import com.fadli.submission2.ui.adapter.FragmentAdapter
import com.fadli.submission2.util.Constanta.EXTRA_USER
import com.fadli.submission2.util.Constanta.TAB_TITLES
import com.fadli.submission2.util.Resource
import com.fadli.submission2.util.ViewStateCallback
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity(), ViewStateCallback<DataUsers?> {

    private lateinit var detailUserBinding : ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)

        viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val username = intent.getStringExtra(EXTRA_USER)

        viewModel.getDetailUser(username).observe(this) {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data)
                is Resource.Error -> onFailed(it.message)
            }
        }

        val fragmentAdapter = FragmentAdapter(this, username.toString())
        detailUserBinding.apply {
            viewPager2.adapter = fragmentAdapter
            TabLayoutMediator(tabs, viewPager2){tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }


    }

    override fun onSuccess(data: DataUsers?) {
        detailUserBinding.apply {
            tvNameDetail.text =data?.name ?:"-"
            tvCompany.text = data?.company?:"-"
            tvLocation.text = data?.location ?:"-"
            tvRepository.text = data?.repository.toString()
            tvFollowers.text = data?.follower.toString()
            tvFollowing.text = data?.following.toString()

            Glide.with(this@DetailUser)
                .load(data?.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(avatarDetail)

            supportActionBar?.title = data?.username?:"-"

                tvMessage?.visibility = invisible
                progressBar?.visibility = invisible

        }
    }

    override fun onLoading() {
        detailUserBinding.apply {
            tvMessage?.visibility = invisible
            progressBar?.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        detailUserBinding.apply {
            if (message == null){
                tvMessage?.text = "data gagal"
                tvMessage?.visibility = visible
            }else{
                tvMessage?.text = message
                tvMessage?.visibility = visible
            }
            progressBar?.visibility = invisible
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}