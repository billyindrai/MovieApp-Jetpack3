package com.billyindrai.subjetpack3.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.billyindrai.subjetpack3.Resources
import com.billyindrai.subjetpack3.data.Repository
import com.billyindrai.subjetpack3.data.entity.MovieEntity

class MovieViewModel(private val repository: Repository): ViewModel() {
    fun getMovieApi(): LiveData<Resources<PagedList<MovieEntity>>> {
        return repository.getMovies()
    }

    fun addToFavorite(movie : MovieEntity){
        val newState = movie.favorite
        repository.setMovieEntity(movie, newState)
    }

    fun getListMovieFavorite(): LiveData<PagedList<MovieEntity>> = repository.getListFavorite()

    fun getMovieById(movieId: Int): LiveData<MovieEntity> = repository.getMovieById(movieId)

    fun deleteMovie(movie: MovieEntity) {
        val newState = !movie.favorite
        repository.setMovieEntity(movie, newState)
    }

    fun getAllMoviesFromDb(sort: String): LiveData<PagedList<MovieEntity>> =
        LivePagedListBuilder(repository.getAllMovieFromDb(sort), 10).build()
}