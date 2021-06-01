package com.billyindrai.subjetpack3.data.remote

import com.google.gson.annotations.SerializedName

data class Result <T> (
    @field:SerializedName("results")
    val results: List<T>
)