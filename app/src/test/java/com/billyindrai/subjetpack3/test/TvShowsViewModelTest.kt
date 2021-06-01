package com.billyindrai.subjetpack3.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.billyindrai.subjetpack3.Resources
import com.billyindrai.subjetpack3.data.DataDummy
import com.billyindrai.subjetpack3.data.Repository
import com.billyindrai.subjetpack3.data.entity.TvShowsEntity
import com.billyindrai.subjetpack3.ui.tvshow.TvShowsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsViewModelTest{
    private lateinit var tvViewModel: TvShowsViewModel

    @Mock
    private lateinit var repository: Repository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resources<PagedList<TvShowsEntity>>>

    @Mock
    private lateinit var tvShowPagedList: PagedList<TvShowsEntity>

    @Mock
    private lateinit var observerShow: Observer<PagedList<TvShowsEntity>>

    @Mock
    private lateinit var obs: Observer<TvShowsEntity>

    @Before
    fun setup(){
        tvViewModel = TvShowsViewModel(repository)
    }
    @Test
    fun getTvShow(){
        val dummyTvShow = Resources.success(tvShowPagedList)
        Mockito.`when`(dummyTvShow.data?.size).thenReturn(5)
        val tvShow = MutableLiveData<Resources<PagedList<TvShowsEntity>>>()
        tvShow.value = dummyTvShow

        Mockito.`when`(repository.getTvShows()).thenReturn(tvShow)
        val tvShowItem = tvViewModel.getTvShowApi().value?.data
        Mockito.verify(repository).getTvShows()
        Assert.assertNotNull(tvShowItem)
        Assert.assertEquals(5, tvShowItem?.size)

        tvViewModel.getTvShowApi().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShow)

    }

    @Test
    fun getFavoriteTvShow(){
        val dummyTv= tvShowPagedList
        Mockito.`when`(dummyTv.size).thenReturn(5)
        val tv = MutableLiveData<PagedList<TvShowsEntity>>()
        tv.value = dummyTv

        Mockito.`when`(repository.getListTvShowFavorite()).thenReturn(tv)
        val tvShowItem = tvViewModel.getListTvShowFavorite().value
        Mockito.verify(repository).getListTvShowFavorite()
        Assert.assertNotNull(tvShowItem)
        Assert.assertEquals(5, tvShowItem?.size)

        tvViewModel.getListTvShowFavorite().observeForever(observerShow)
        Mockito.verify(observerShow).onChanged(dummyTv)
    }

    @Test
    fun getShowById(){
        val dummyTvShow = DataDummy.getTvShows()[1]
        val tvShowId = DataDummy.getTvShows()[1].id

        val tvShowDummy = MutableLiveData<TvShowsEntity>()
        tvShowDummy.value = dummyTvShow

        Mockito.`when`(repository.getTvShowById(tvShowId)).thenReturn(tvShowDummy)

        val tvData = tvViewModel.getTvShowById(tvShowId).value

        Assert.assertNotNull(tvData)

        Assert.assertEquals(dummyTvShow.id, tvData?.id)
        Assert.assertEquals(dummyTvShow.title, tvData?.title)
        Assert.assertEquals(dummyTvShow.poster, tvData?.poster)
        Assert.assertEquals(dummyTvShow.date, tvData?.date)
        Assert.assertEquals(dummyTvShow.rating, tvData?.rating)
        Assert.assertEquals(dummyTvShow.episodes, tvData?.episodes)
        Assert.assertEquals(dummyTvShow.overview, tvData?.overview)

        tvViewModel.getTvShowById(tvShowId).observeForever(obs)
        Mockito.verify(obs).onChanged(dummyTvShow)

    }

    @Test
    fun addToFavorite(){
        val dummyShow = DataDummy.getTvShows()[1]
        tvViewModel.addToFavorite(dummyShow)
        Mockito.verify(repository).setTvShowEntity(dummyShow, dummyShow.favorite)
    }
}