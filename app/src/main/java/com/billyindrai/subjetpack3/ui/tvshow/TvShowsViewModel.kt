package com.billyindrai.subjetpack3.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.billyindrai.subjetpack3.Resources
import com.billyindrai.subjetpack3.data.Repository
import com.billyindrai.subjetpack3.data.entity.TvShowsEntity

class TvShowsViewModel(private val repository: Repository): ViewModel() {
    fun getTvShowApi(): LiveData<Resources<PagedList<TvShowsEntity>>> {
        return repository.getTvShows()
    }

    fun addToFavorite(tvShow: TvShowsEntity) {
        val newState = tvShow.favorite
        repository.setTvShowEntity(tvShow, newState)
    }

    fun getListTvShowFavorite(): LiveData<PagedList<TvShowsEntity>> =
        repository.getListTvShowFavorite()

    fun getTvShowById(tvShowId: Int): LiveData<TvShowsEntity> = repository.getTvShowById(tvShowId)

    fun deleteTvShows(tvShow: TvShowsEntity) {
        val newState = !tvShow.favorite
        repository.setTvShowEntity(tvShow, newState)
    }

    fun getAllTVShowsFromDb(sort: String): LiveData<PagedList<TvShowsEntity>> =
        LivePagedListBuilder(repository.getAllTvFromDb(sort), 10).build()

}