package com.fadli.submission2.data

import com.squareup.moshi.Json

data class DataUsers(

    @field:Json(name = "avatar_url")
    val avatar: String,

    @field:Json(name = "login")
    val username: String,

    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "company")
    val company: String,

    @field:Json(name = "location")
    val location: String,

    @field:Json(name = "public_repos")
    val repository: Int,

    @field:Json(name = "followers")
    val follower: Int,

    @field:Json(name = "following")
    val following: Int

)
