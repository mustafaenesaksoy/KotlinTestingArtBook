package com.enesaksoy.ikotlinartbooktest.roomdbtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.enesaksoy.ikotlinartbooktest.getOrAwaitValue
import com.enesaksoy.ikotlinartbooktest.roomdb.Art
import com.enesaksoy.ikotlinartbooktest.roomdb.ArtDao
import com.enesaksoy.ikotlinartbooktest.roomdb.ArtDatabase
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class RoomDbTest {

    @get:Rule
    var hiltRule= HiltAndroidRule(this)

    @Inject
    @Named("TestRoom")
    lateinit var database : ArtDatabase

    private lateinit var dao : ArtDao
    @get:Rule
    var intantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    fun setup(){
        /*database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),ArtDatabase::class.java
        ).allowMainThreadQueries().build()*/

        hiltRule.inject()
        dao = database.artDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertArtTest() = runBlockingTest{
        val art = Art("Mona Lisa","Da Vinci",1300,"www.example.com",3)
        dao.insertArt(art)
        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(art)
    }

    @Test
    fun deleteArtTest()= runBlocking{
        val art = Art("Mona Lisa","Da Vinci",1300,"www.example.com",2)
        dao.insertArt(art)
        dao.deleteArt(art)
        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(art)
    }
}