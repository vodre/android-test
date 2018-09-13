package com.labs.androidcodetest.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Repo {

    @SerializedName("total_count")
    @Expose
    var totalCount: Int = 0
    @SerializedName("incomplete_results")
    @Expose
    var isIncompleteResults: Boolean = false
    @SerializedName("items")
    @Expose
    var items: List<Item>? = null
}
