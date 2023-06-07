package com.fadli.submission2.ui.follower

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fadli.submission2.R
import com.fadli.submission2.data.DataUsers
import com.fadli.submission2.ui.adapter.UserAdapter
import com.fadli.submission2.util.ViewStateCallback
import com.fadli.submission2.databinding.FragmentFollowerBinding
import com.fadli.submission2.util.Resource


class FollowerFragment : Fragment(), ViewStateCallback<List<DataUsers>> {

    companion object {
        private const val KEY_BUNDLE = "USERNAME"

        fun newInstance(username: String): Fragment{
            return FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }

    private lateinit var viewModel: FollowerViewModel
    private var username : String? = null
    private lateinit var userAdapter: UserAdapter
    private val followerBinding: FragmentFollowerBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[FollowerViewModel::class.java]
        userAdapter = UserAdapter()
        followerBinding.rvFollower.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        viewModel.getUserFollowers(username.toString()).observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        }
    }

    override fun onSuccess(data: List<DataUsers>) {
        userAdapter.setAllData(data)
        followerBinding.apply {
            tvMessage.visibility = invisible
            progressBar.visibility = invisible
            rvFollower.visibility = visible
        }
    }

    override fun onLoading() {
        followerBinding.apply {
            tvMessage.visibility = invisible
            progressBar.visibility = visible
            rvFollower.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        followerBinding.apply {
            if (message == null){
                tvMessage.text = resources.getString(R.string.followers_not_found, username)
                tvMessage.visibility = visible
            }else{
                tvMessage.text = message
                tvMessage.visibility = visible
            }
            progressBar.visibility = invisible
            rvFollower.visibility = invisible
        }
    }


}