package com.billyindrai.subjetpack3.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("poster_path")
    val poster: String,

    @field:SerializedName("original_title")
    val title: String,

    @field:SerializedName("release_date")
    val date: String,

    @field:SerializedName("vote_average")
    val rating: Double,

    @field:SerializedName("runtime")
    var duration: Int,

    @field:SerializedName("overview")
    val overview: String

): Parcelable