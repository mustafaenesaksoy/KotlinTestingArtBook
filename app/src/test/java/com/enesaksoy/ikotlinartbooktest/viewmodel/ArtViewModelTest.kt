package com.enesaksoy.ikotlinartbooktest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enesaksoy.ikotlinartbooktest.MainCoroutineRule
import com.enesaksoy.ikotlinartbooktest.getOrAwaitValueTest
import com.enesaksoy.ikotlinartbooktest.repo.FakeArtRepository
import com.enesaksoy.ikotlinartbooktest.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var viewmodel : ArtViewModel

    @Before
    fun setup(){
        viewmodel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`(){
        viewmodel.makeArt("Mona Lisa","Da Vinci","")
        val value = viewmodel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`(){
        viewmodel.makeArt("","Da Vinci","1300")
        val value = viewmodel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artist name returns error`(){
        viewmodel.makeArt("Mona Lisa","","1300")
        val value = viewmodel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}