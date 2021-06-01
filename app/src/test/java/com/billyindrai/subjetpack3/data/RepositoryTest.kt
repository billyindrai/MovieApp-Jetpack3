package com.billyindrai.subjetpack3.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.billyindrai.subjetpack3.AppExecutors
import com.billyindrai.subjetpack3.Resources
import com.billyindrai.subjetpack3.data.entity.MovieEntity
import com.billyindrai.subjetpack3.data.entity.TvShowsEntity
import com.billyindrai.subjetpack3.data.local.LocalData
import com.billyindrai.subjetpack3.data.remote.api.RemoteDataSource
import com.bumptech.glide.load.engine.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class RepositoryTest {
    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalData::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val repository = FakeRepo(remote, local, appExecutors)
    private val listMovie = DataDummy.generateMovieData()
    private val listTvShow = DataDummy.generateTvShowData()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getAllMovies(){
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getAllMovieFavorite()).thenReturn(dataSourceFactory)
        repository.getMovies()

        val moviesEntities =
            Resources.success(PagedList.mockPagedList(DataDummy.generateMovieData()))
        verify(local).getAllMovieFavorite()
        org.junit.Assert.assertNotNull(moviesEntities.data)
        assertEquals(listMovie.size.toLong(), moviesEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvShows(){
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowsEntity>
        Mockito.`when`(local.getAllTvShowFavorite()).thenReturn(dataSourceFactory)
        repository.getTvShows()

        val showItem = Resources.success(PagedList.mockPagedList(DataDummy.generateTvShowData()))
        verify (local).getAllTvShowFavorite()
        Assert.assertNotNull(showItem.data)
        org.junit.Assert.assertNotNull(showItem.data)
        assertEquals(listTvShow.size.toLong(), showItem.data?.size?.toLong())

    }

    @Test
    fun geListMovieFavorite(){
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getListMovieFavorite()).thenReturn(dataSourceFactory)
        repository.getListMovieFavorite()
        val movieItem = Resources.success(PagedList.mockPagedList(DataDummy.generateMovieData()))
        verify (local).getListMovieFavorite()
        Assert.assertNotNull(movieItem.data)
        assertEquals(listMovie.size.toLong(), movieItem.data?.size?.toLong())
    }

    @Test
    fun geListTvShowFavorite(){
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowsEntity>
        Mockito.`when`(local.getListTvShowFavorite()).thenReturn(dataSourceFactory)
        repository.getListTvShowFavorite()
        val showItem = Resources.success(PagedList.mockPagedList(DataDummy.generateTvShowData()))
        verify (local).getListTvShowFavorite()
        Assert.assertNotNull(showItem.data)
        assertEquals(listMovie.size.toLong(), showItem.data?.size?.toLong())
    }

    @Test
    fun setMovieFavorite(){
        val dummyMovie = DataDummy.getMovies()[1]
        local.setFavorite(dummyMovie, dummyMovie.favorite)
        verify(local).setFavorite(dummyMovie, dummyMovie.favorite)
    }

    @Test
    fun setTvShowFavorite(){
        val dummyShow = DataDummy.getTvShows()[1]
        local.setTvShowFavorite(dummyShow, dummyShow.favorite)
        verify(local).setTvShowFavorite(dummyShow, dummyShow.favorite)
    }

    @Test
    fun getMovieById(){
        val dummyMovie = DataDummy.getMovies()[1]
        val movieId = DataDummy.getMovies()[1].id

        val movieDummy = MutableLiveData<MovieEntity>()
        movieDummy.value = dummyMovie

        Mockito.`when`(local.getMovieById(movieId)).thenReturn(movieDummy)
        repository.getMovieById(movieId)
        verify(local).getMovieById(movieId)

        val movieData = local.getMovieById(movieId).value

        org.junit.Assert.assertNotNull(movieData)

        org.junit.Assert.assertEquals(dummyMovie.id, movieData?.id)
        org.junit.Assert.assertEquals(dummyMovie.title, movieData?.title)
        org.junit.Assert.assertEquals(dummyMovie.poster, movieData?.poster)
        org.junit.Assert.assertEquals(dummyMovie.date, movieData?.date)
        org.junit.Assert.assertEquals(dummyMovie.rating, movieData?.rating)
        org.junit.Assert.assertEquals(dummyMovie.duration, movieData?.duration)
        org.junit.Assert.assertEquals(dummyMovie.overview, movieData?.overview)
    }

    @Test
    fun getTvShowById(){
        val dummyTvShow = DataDummy.getTvShows()[1]
        val tvShowId = DataDummy.getTvShows()[1].id

        val tvShowDummy = MutableLiveData<TvShowsEntity>()
        tvShowDummy.value = dummyTvShow

        Mockito.`when`(local.getTvShowById(tvShowId)).thenReturn(tvShowDummy)
        repository.getTvShowById(tvShowId)
        verify(local).getTvShowById(tvShowId)

        val tvData = local.getTvShowById(tvShowId).value

        org.junit.Assert.assertNotNull(tvData)

        org.junit.Assert.assertEquals(dummyTvShow.id, tvData?.id)
        org.junit.Assert.assertEquals(dummyTvShow.title, tvData?.title)
        org.junit.Assert.assertEquals(dummyTvShow.poster, tvData?.poster)
        org.junit.Assert.assertEquals(dummyTvShow.date, tvData?.date)
        org.junit.Assert.assertEquals(dummyTvShow.rating, tvData?.rating)
        org.junit.Assert.assertEquals(dummyTvShow.episodes, tvData?.episodes)
        org.junit.Assert.assertEquals(dummyTvShow.overview, tvData?.overview)
    }


}