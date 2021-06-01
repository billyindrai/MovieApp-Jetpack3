package com.billyindrai.subjetpack3.data

import android.util.Log
import com.billyindrai.subjetpack3.data.entity.MovieEntity
import com.billyindrai.subjetpack3.data.entity.TvShowsEntity
import com.billyindrai.subjetpack3.data.remote.Movie
import com.billyindrai.subjetpack3.data.remote.Result
import com.billyindrai.subjetpack3.data.remote.TvShows
import com.billyindrai.subjetpack3.data.remote.api.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataDummy {
    fun getMovies(): List<MovieEntity>{
        val list = ArrayList<MovieEntity>()

        list.add(
            MovieEntity(
                460465,
                "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg",
                "Mortal Kombat",
                "2021-04-07",
                7.8,
                110,
                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe."
            )
        )
        list.add(
            MovieEntity(
                399566,
                "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
                "Godzilla vs. Kong",
                "2021-03-24",
                8.1,
                113,
                "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages."
            )
        )
        return list
    }

    fun getTvShows(): List<TvShowsEntity>{
        val list = ArrayList<TvShowsEntity>()

        list.add(TvShowsEntity
            (
            95557,
            "Invincible",
            "/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg",
            "2021-03-26",
            8.9,
            8,
            "Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage."
            )
        )
        list.add(TvShowsEntity
            (
            88396,
            "The Falcon and the Winter Soldier",
            "/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
            "2021-03-19",
            7.9,
            6,
            "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience."
            )
        )
        return list
    }

    fun generateMovieData(): List<MovieEntity>{
        val apiService = Config.getApiService()
        val _movie = ArrayList<Movie>()
        val TAG = "DataDummy"
        val movieList = ArrayList<MovieEntity>()

        val client = apiService.getMovie()
        client.enqueue(object : Callback<Result<Movie>> {
            override fun onResponse(call: Call<Result<Movie>>, response: Response<Result<Movie>>) {
                if (response.isSuccessful){
                    val respon = response.body()?.results
                    _movie.addAll(respon!!)
                    for (data in _movie){
                        val mMovie = MovieEntity(
                            data.id,
                            data.poster,
                            data.title,
                            data.date,
                            data.rating,
                            data.duration,
                            data.overview
                        )
                        movieList.add(mMovie)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Result<Movie>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return movieList
    }

    fun generateTvShowData(): List<TvShowsEntity>{
        val apiService = Config.getApiService()
        val TAG = "DataDummy"

        val tvShow = ArrayList<TvShows>()
        val client = apiService.getTvShow()

        val showList = ArrayList<TvShowsEntity>()
        client.enqueue(object: Callback<Result<TvShows>> {
            override fun onResponse(call: Call<Result<TvShows>>, response: Response<Result<TvShows>>) {
                if(response.isSuccessful){
                    tvShow.addAll(response.body()?.results!!)
                    for(data in tvShow){
                        val show = TvShowsEntity(
                            data.id,
                            data.title,
                            data.poster,
                            data.date,
                            data.rating,
                            data.episodes,
                            data.overview
                        )
                        showList.add(show)
                    }
                }else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Result<TvShows>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })

        return showList
    }
}