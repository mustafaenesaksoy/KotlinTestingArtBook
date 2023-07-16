package com.enesaksoy.ikotlinartbooktest.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.enesaksoy.ikotlinartbooktest.launchFragmentInHiltContainer
import com.enesaksoy.ikotlinartbooktest.repo.FakeArtRepositoryTest
import com.enesaksoy.ikotlinartbooktest.viewmodel.ArtViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.enesaksoy.ikotlinartbooktest.R
import com.enesaksoy.ikotlinartbooktest.adapter.ImageAdapter
import com.enesaksoy.ikotlinartbooktest.getOrAwaitValue
import com.google.common.truth.Truth.assertThat


@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ApiFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun selectImage(){
        val viewmodelTest = ArtViewModel(FakeArtRepositoryTest())
        val navcontroller = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ApiFragment>(factory = fragmentFactory){
            viewmodel = viewmodelTest
            adapter.imagelist = listOf("example.com")
            Navigation.setViewNavController(requireView(),navcontroller)
        }


        Espresso.onView(withId(R.id.recyclerImageView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.imageHolder>(
                0,ViewActions.click()
            )
        )
        Mockito.verify(navcontroller).popBackStack()

        assertThat(viewmodelTest.selectedImageUrl.getOrAwaitValue()).isEqualTo("example.com")
    }
}