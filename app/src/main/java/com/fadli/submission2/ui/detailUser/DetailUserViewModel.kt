package com.fadli.submission2.ui.detailUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fadli.submission2.api.RetrofitService
import com.fadli.submission2.data.DataUsers
import com.fadli.submission2.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel:ViewModel() {

    private val retrofit = RetrofitService.create()
    private val dataUsers = MutableLiveData<Resource<DataUsers>>()

    fun getDetailUser(username: String?): LiveData<Resource<DataUsers>>{
        retrofit.getDetailUser(username!!).enqueue(object : Callback<DataUsers>{
            override fun onResponse(call: Call<DataUsers>, response: Response<DataUsers>) {
                val result = response.body()
                dataUsers.postValue(Resource.Success(result))
            }

            override fun onFailure(call: Call<DataUsers>, t: Throwable) {

            }
        })
        return dataUsers
    }

}