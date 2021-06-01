package com.billyindrai.subjetpack3.data.remote.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.billyindrai.subjetpack3.EspressoIdling
import com.billyindrai.subjetpack3.data.Repository
import com.billyindrai.subjetpack3.data.remote.Movie
import com.billyindrai.subjetpack3.data.remote.Result
import com.billyindrai.subjetpack3.data.remote.TvShows
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class RemoteDataSource {
    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }

    private var apiService = Config.getApiService()
    private val TAG = Repository::class.java.simpleName

    fun getAllMovie(): LiveData<Responses<List<Movie>>>{
        EspressoIdling.increment()
        val movie = MutableLiveData<Responses<List<Movie>>>()
        val client = apiService.getMovie()
        client.enqueue(object : Callback<Result<Movie>> {
            override fun onResponse(call: Call<Result<Movie>>, response: Response<Result<Movie>>) {
                EspressoIdling.decrement()
                if(response.isSuccessful){
                    movie.value = response.body()?.results?.let { Responses.success(it) }

                }else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Result<Movie>>, t: Throwable) {
                EspressoIdling.decrement()
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
        return movie
    }

    fun getAllTvShow(): LiveData<Responses<List<TvShows>>> {
        EspressoIdling.increment()
        val tvShow = MutableLiveData<Responses<List<TvShows>>>()
        val client = apiService.getTvShow()
        client.enqueue(object: Callback<Result<TvShows>> {
            override fun onResponse(call: Call<Result<TvShows>>, response: Response<Result<TvShows>>) {
                EspressoIdling.decrement()
                if(response.isSuccessful){
                    tvShow.value = response.body()?.results?.let { Responses.success(it) }
                }else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Result<TvShows>>, t: Throwable) {
                EspressoIdling.decrement()
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
        return tvShow
    }
}