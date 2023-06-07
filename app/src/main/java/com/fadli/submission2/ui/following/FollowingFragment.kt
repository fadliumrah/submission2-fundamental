package com.fadli.submission2.ui.following

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadli.submission2.R
import com.fadli.submission2.data.DataUsers
import com.fadli.submission2.ui.adapter.UserAdapter
import com.fadli.submission2.util.ViewStateCallback
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fadli.submission2.databinding.FragmentFollowingBinding
import com.fadli.submission2.util.Resource

class FollowingFragment : Fragment(), ViewStateCallback<List<DataUsers>> {

    companion object {
        private const val KEY_BUNDLE = "USERNAME"
        fun newInstance(username: String): Fragment{
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }

    private lateinit var viewModel: FollowingViewModel
    private var username : String? = null
    private lateinit var userAdapter: UserAdapter
    private val followingBinding: FragmentFollowingBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[FollowingViewModel::class.java]
        userAdapter = UserAdapter()
        followingBinding.rvFollowing.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        viewModel.getUserFollowing(username.toString()).observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        }
    }

    override fun onSuccess(data: List<DataUsers>) {
        userAdapter.setAllData(data)
        followingBinding.apply {
            tvMessage.visibility = invisible
            progressBar.visibility = invisible
            rvFollowing.visibility = visible
        }
    }

    override fun onLoading() {
        followingBinding.apply {
            tvMessage.visibility = invisible
            progressBar.visibility = visible
            rvFollowing.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        followingBinding.apply {
            if (message == null){
                tvMessage.text = resources.getString(R.string.followers_not_found, username)
                tvMessage.visibility = visible
            }else{
                tvMessage.text = message
                tvMessage.visibility = visible
            }
            progressBar.visibility = invisible
            rvFollowing.visibility = invisible
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

}