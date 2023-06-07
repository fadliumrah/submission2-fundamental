package com.fadli.submission2.ui.home

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadli.submission2.R
import com.fadli.submission2.data.DataUsers
import com.fadli.submission2.databinding.ActivityMainBinding
import com.fadli.submission2.ui.adapter.UserAdapter
import com.fadli.submission2.util.Resource
import com.fadli.submission2.util.ViewStateCallback

class MainActivity : AppCompatActivity(), ViewStateCallback<List<DataUsers>> {

    private lateinit var homeBinding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var userQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        userAdapter = UserAdapter()
        homeBinding.mainSearch.rvItemUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_view).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                userQuery = query.toString()
                searchView.clearFocus()
                viewModel.searchUser(userQuery).observe(this@MainActivity) {
                    when (it) {
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                        is Resource.Error -> onFailed(it.message)
                    }
                }
                viewModel
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onLoading() {
        homeBinding.mainSearch.apply {
            searchIcon.visibility =invisible
            tvMessage.visibility = invisible
            progressBar.visibility = visible
            rvItemUser.visibility = invisible
            sib3.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        homeBinding.mainSearch.apply {
            if (message == null){
                searchIcon.apply {
                    setImageResource(R.drawable.ic_baseline_search_off_24)
                    visibility = visible
                }
                tvMessage.apply {
                    text = resources.getString(R.string.UserNotFound)
                    visibility = visible
                }

            }else{
                searchIcon.apply {
                    setImageResource(R.drawable.ic_baseline_error_outline_24)
                    visibility = visible
                }
                tvMessage.apply {
                    text = message
                    visibility = visible
                }
            }
            progressBar.visibility = invisible
            rvItemUser.visibility = invisible
            sib3.visibility = invisible
        }
    }

    override fun onSuccess(data: List<DataUsers>) {
        userAdapter.setAllData(data)
        homeBinding.mainSearch.apply {
            searchIcon.visibility = invisible
            tvMessage.visibility = invisible
            progressBar.visibility = invisible
            rvItemUser.visibility = visible
            sib3.visibility = invisible
        }
    }

}