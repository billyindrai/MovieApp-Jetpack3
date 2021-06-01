package com.billyindrai.subjetpack3.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.billyindrai.subjetpack3.Resources
import com.billyindrai.subjetpack3.data.DataDummy
import com.billyindrai.subjetpack3.data.Repository
import com.billyindrai.subjetpack3.data.entity.MovieEntity
import com.billyindrai.subjetpack3.ui.movie.MovieViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest{
    private lateinit var viewModel: MovieViewModel


    @Mock
    private lateinit var repository: Repository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var moviePagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var observerMovie: Observer<Resources<PagedList<MovieEntity>>>

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var obs: Observer<MovieEntity>

    @Before
    fun setup(){
        viewModel = MovieViewModel(repository)
    }

    @Test
    fun getMovie(){
        val dummyMovie = Resources.success(pagedList)
        Mockito.`when`(dummyMovie.data?.size).thenReturn(5)
        val movie = MutableLiveData<Resources<PagedList<MovieEntity>>>()
        movie.value = dummyMovie

        Mockito.`when`(repository.getMovies()).thenReturn(movie)
        val movieItem = viewModel.getMovieApi().value?.data
        Mockito.verify(repository).getMovies()
        Assert.assertNotNull(movieItem)
        Assert.assertEquals(5, movieItem?.size)

        viewModel.getMovieApi().observeForever(observerMovie)
        Mockito.verify(observerMovie).onChanged(dummyMovie)
    }

    @Test
    fun getFavoriteMovie(){
        val dummyMovie = moviePagedList
        Mockito.`when`(dummyMovie.size).thenReturn(5)
        val movie = MutableLiveData<PagedList<MovieEntity>>()
        movie.value = dummyMovie

        Mockito.`when`(repository.getListFavorite()).thenReturn(movie)
        val movieItem = viewModel.getListMovieFavorite().value
        Mockito.verify(repository).getListFavorite()
        Assert.assertNotNull(movieItem)
        Assert.assertEquals(5, movieItem?.size)

        viewModel.getListMovieFavorite().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun getMovieById(){
        val dummyMovie = DataDummy.getMovies()[1]
        val movieId = DataDummy.getMovies()[1].id

        val movieDummy = MutableLiveData<MovieEntity>()
        movieDummy.value = dummyMovie

        Mockito.`when`(repository.getMovieById(movieId)).thenReturn(movieDummy)

        val movieData = viewModel.getMovieById(movieId).value

        Assert.assertNotNull(movieData)

        Assert.assertEquals(dummyMovie.id, movieData?.id)
        Assert.assertEquals(dummyMovie.title, movieData?.title)
        Assert.assertEquals(dummyMovie.poster, movieData?.poster)
        Assert.assertEquals(dummyMovie.date, movieData?.date)
        Assert.assertEquals(dummyMovie.rating, movieData?.rating)
        Assert.assertEquals(dummyMovie.duration, movieData?.duration)
        Assert.assertEquals(dummyMovie.overview, movieData?.overview)

        viewModel.getMovieById(movieId).observeForever(obs)
        Mockito.verify(obs).onChanged(dummyMovie)

    }

    @Test
    fun addToFavorite(){
        val dummyMovie = DataDummy.getMovies()[1]
        viewModel.addToFavorite(dummyMovie)
        Mockito.verify(repository).setMovieEntity(dummyMovie, dummyMovie.favorite)
    }
}