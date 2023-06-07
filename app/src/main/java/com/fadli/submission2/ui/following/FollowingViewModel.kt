package com.fadli.submission2.ui.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fadli.submission2.api.RetrofitService
import com.fadli.submission2.data.DataUsers
import com.fadli.submission2.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val retrofit = RetrofitService.create()
    private val listDataUsers = MutableLiveData<Resource<List<DataUsers>>>()

    fun getUserFollowing(username : String) : LiveData<Resource<List<DataUsers>>> {
        listDataUsers.postValue(Resource.Loading())
        retrofit.getFollowing(username).enqueue(object : Callback<List<DataUsers>> {
            override fun onResponse(call: Call<List<DataUsers>>, response: Response<List<DataUsers>>) {
                val list = response.body()
                if (list.isNullOrEmpty()){
                    listDataUsers.postValue(Resource.Error(null))
                }else{
                    listDataUsers.postValue(Resource.Success(list))
                }
            }

            override fun onFailure(call: Call<List<DataUsers>>, t: Throwable) {
                listDataUsers.postValue(Resource.Error(t.message))
            }
        })
        return listDataUsers
    }
}