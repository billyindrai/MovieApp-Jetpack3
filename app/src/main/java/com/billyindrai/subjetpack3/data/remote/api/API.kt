package com.billyindrai.subjetpack3.data.remote.api

import com.billyindrai.subjetpack3.data.remote.Movie
import com.billyindrai.subjetpack3.data.remote.Result
import com.billyindrai.subjetpack3.data.remote.TvShows
import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("movie/popular?api_key=b743213571e7b5422b800461f07dd2f0")
    fun getMovie(): Call<Result<Movie>>

    @GET("tv/popular?api_key=b743213571e7b5422b800461f07dd2f0")
    fun getTvShow(): Call<Result<TvShows>>
}