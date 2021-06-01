package com.billyindrai.subjetpack3.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.billyindrai.subjetpack3.AppExecutors
import com.billyindrai.subjetpack3.Resources
import com.billyindrai.subjetpack3.data.entity.MovieEntity
import com.billyindrai.subjetpack3.data.entity.TvShowsEntity
import com.billyindrai.subjetpack3.data.local.LocalData
import com.billyindrai.subjetpack3.data.remote.Movie
import com.billyindrai.subjetpack3.data.remote.TvShows
import com.billyindrai.subjetpack3.data.remote.api.RemoteDataSource
import com.billyindrai.subjetpack3.data.remote.api.Responses
import java.util.concurrent.Executors

open class FakeRepo(
    private val remoteDataSource: RemoteDataSource,
    private val localData: LocalData,
    private val appExecutors: AppExecutors
) {
    private val executorsService = Executors.newSingleThreadExecutor()

    fun getMovies(): LiveData<Resources<PagedList<MovieEntity>>> {
        return object : NetworkBoundSource<PagedList<MovieEntity>, List<Movie>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(4)
                    setPageSize(4)
                }.build()
                return LivePagedListBuilder(localData.getAllMovieFavorite(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<Responses<List<Movie>>> {
                return remoteDataSource.getAllMovie()
            }

            override fun saveCallResult(data: List<Movie>) {
                val movieList = ArrayList<MovieEntity>()
                for(data in data){
                    val movie = MovieEntity(
                        data.id,
                        data.poster,
                        data.title,
                        data.date,
                        data.rating,
                        data.duration,
                        data.overview
                    )
                    movieList.add(movie)
                }

                localData.insertMovie(movieList)
            }

        }.asLiveData()
    }

    fun getTvShows(): LiveData<Resources<PagedList<TvShowsEntity>>> {
        return object : NetworkBoundSource<PagedList<TvShowsEntity>, List<TvShows>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowsEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localData.getAllTvShowFavorite(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowsEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<Responses<List<TvShows>>> {
                return remoteDataSource.getAllTvShow()
            }

            override fun saveCallResult(data: List<TvShows>) {
                val tvShowList = ArrayList<TvShowsEntity>()
                for(data in data){
                    val tvShow = TvShowsEntity(
                        data.id,
                        data.title,
                        data.poster,
                        data.date,
                        data.rating,
                        data.episodes,
                        data.overview
                    )
                    tvShowList.add(tvShow)
                }
                localData.insertTvShow(tvShowList)
            }

        }.asLiveData()
    }

    fun setMovieFavorite(movie: MovieEntity, state: Boolean) =
        executorsService.execute { localData.setFavorite(movie, state) }

    fun setTvShowFavorite(tv: TvShowsEntity, state: Boolean) =
        executorsService.execute { localData.setTvShowFavorite(tv, state) }

    fun getListMovieFavorite(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localData.getListMovieFavorite(), config).build()
    }

    fun getListTvShowFavorite(): LiveData<PagedList<TvShowsEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(4)
            .setInitialLoadSizeHint(4)
            .build()
        return LivePagedListBuilder(localData.getListTvShowFavorite(), config).build()
    }

    fun getMovieById(movieId: Int): LiveData<MovieEntity> =
        localData.getMovieById(movieId)

    fun getTvShowById(tvId: Int): LiveData<TvShowsEntity> =
        localData.getTvShowById(tvId)

}