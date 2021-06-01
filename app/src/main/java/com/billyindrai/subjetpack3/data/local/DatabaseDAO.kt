package com.billyindrai.subjetpack3.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.billyindrai.subjetpack3.data.entity.MovieEntity
import com.billyindrai.subjetpack3.data.entity.TvShowsEntity

@Dao
interface DatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: List<MovieEntity>)

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Query("SELECT * FROM movieentity")
    fun getAllMovieFavorite(): DataSource.Factory<Int, MovieEntity>

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM movieentity WHERE favorite = 1")
    fun getListFavoriteMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieentity WHERE id = :movieId")
    fun getMovieById(movieId: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTvShow(tvShow: List<TvShowsEntity>)

    @Delete
    suspend fun deleteTvShow(tvShow: TvShowsEntity)

    @Query("SELECT * FROM tvshowsentity")
    fun getAllTvShowFavorite(): DataSource.Factory<Int, TvShowsEntity>

    @Update
    fun updateShow(tvShow: TvShowsEntity)

    @Query("SELECT * FROM tvshowsentity WHERE favorite = 1")
    fun getListTvShowFavorite(): DataSource.Factory<Int, TvShowsEntity>

    @Query("SELECT * FROM tvshowsentity WHERE id = :tvShowId")
    fun getTvShowById(tvShowId: Int): LiveData<TvShowsEntity>

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getAllMoviesFromDB(query: SimpleSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @RawQuery(observedEntities = [TvShowsEntity::class])
    fun getAllTvFromDB(query: SimpleSQLiteQuery): DataSource.Factory<Int, TvShowsEntity>
}