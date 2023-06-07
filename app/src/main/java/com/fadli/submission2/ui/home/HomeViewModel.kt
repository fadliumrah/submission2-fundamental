package com.fadli.submission2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fadli.submission2.api.ApiService
import com.fadli.submission2.api.RetrofitService
import com.fadli.submission2.data.SearchResponse
import com.fadli.submission2.data.DataUsers
import com.fadli.submission2.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private val listDataUsers = MutableLiveData<Resource<List<DataUsers>>>()
    private val retrofit: ApiService = RetrofitService.create()


    fun searchUser(query: String): LiveData<Resource<List<DataUsers>>>{
        listDataUsers.postValue(Resource.Loading())
        retrofit.searchUsers(query).enqueue(object : Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val list = response.body()?.items
                if (list.isNullOrEmpty()){
                    listDataUsers.postValue(Resource.Error(null))
                }else{
                    listDataUsers.postValue(Resource.Success(list))
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                listDataUsers.postValue(Resource.Error(t.message))
            }
        })
        return listDataUsers
    }

}