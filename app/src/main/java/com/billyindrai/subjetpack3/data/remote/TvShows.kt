package com.billyindrai.subjetpack3.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShows(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("poster_path")
    val poster: String,

    @field:SerializedName("original_name")
    val title: String,

    @field:SerializedName("first_air_date")
    val date: String,

    @field:SerializedName("vote_average")
    val rating: Double,

    @field:SerializedName("number_of_episodes")
    var episodes: Int,

    @field:SerializedName("overview")
    val overview: String

): Parcelable