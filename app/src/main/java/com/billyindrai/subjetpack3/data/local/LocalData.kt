package com.billyindrai.subjetpack3.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.billyindrai.subjetpack3.Sorting
import com.billyindrai.subjetpack3.data.entity.MovieEntity
import com.billyindrai.subjetpack3.data.entity.TvShowsEntity

class LocalData private constructor(
private val databaseDAO: DatabaseDAO){
    companion object{
        private var INSTANCE : LocalData? = null

        fun getInstance(databaseDAO: DatabaseDAO): LocalData = INSTANCE?: LocalData(databaseDAO)
    }

    fun insertMovie(movieEntity: List<MovieEntity>) = databaseDAO.insertMovie(movieEntity)
    fun insertTvShow(show: List<TvShowsEntity>) = databaseDAO.insertTvShow(show)


    fun getAllMovieFavorite(): androidx.paging.DataSource.Factory<Int, MovieEntity> = databaseDAO.getAllMovieFavorite()
    fun getAllTvShowFavorite(): androidx.paging.DataSource.Factory<Int, TvShowsEntity> = databaseDAO.getAllTvShowFavorite()


    fun setFavorite(movie: MovieEntity, newState: Boolean){
        movie.favorite = newState
        databaseDAO.updateMovie(movie)
    }
    fun setTvShowFavorite(tvShow: TvShowsEntity, newState: Boolean){
        tvShow.favorite = newState
        databaseDAO.updateShow(tvShow)
    }


    fun getListMovieFavorite(): androidx.paging.DataSource.Factory<Int, MovieEntity> = databaseDAO.getListFavoriteMovie()
    fun getListTvShowFavorite(): androidx.paging.DataSource.Factory<Int, TvShowsEntity> = databaseDAO.getListTvShowFavorite()

    fun getMovieById(movieId: Int): LiveData<MovieEntity> = databaseDAO.getMovieById(movieId)
    fun getTvShowById(showId: Int): LiveData<TvShowsEntity> = databaseDAO.getTvShowById(showId)

    fun getAllMovieFromDb(sort: String): DataSource.Factory<Int, MovieEntity>  {
        val query = Sorting.getSortedQuery(Sorting.TYPE_MOVIE, sort)
        return databaseDAO.getAllMoviesFromDB(query)
    }
    fun getAllTvFromDb(sort: String): DataSource.Factory<Int, TvShowsEntity>  {
        val query = Sorting.getSortedQuery(Sorting.TYPE_TVSHOW, sort)
        return databaseDAO.getAllTvFromDB(query)
    }

    suspend fun deleteMovie(movie: MovieEntity) {
        databaseDAO.delete(movie)
    }

    suspend fun deleteTvShows(tv: TvShowsEntity) {
        databaseDAO.deleteTvShow(tv)
    }

}