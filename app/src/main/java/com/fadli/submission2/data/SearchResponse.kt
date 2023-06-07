package com.fadli.submission2.data

import com.squareup.moshi.Json

data class SearchResponse(@field:Json(name = "items") val items: List<DataUsers>)
