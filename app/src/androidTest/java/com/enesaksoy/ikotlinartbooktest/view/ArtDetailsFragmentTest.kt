package com.enesaksoy.ikotlinartbooktest.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.enesaksoy.ikotlinartbooktest.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.enesaksoy.ikotlinartbooktest.R
import com.enesaksoy.ikotlinartbooktest.getOrAwaitValue
import com.enesaksoy.ikotlinartbooktest.repo.FakeArtRepositoryTest
import com.enesaksoy.ikotlinartbooktest.roomdb.Art
import com.enesaksoy.ikotlinartbooktest.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat


@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDetailsFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory : ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickImageViewToDetailsToApi(){
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<DetailsFragment>( factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.imageView)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(DetailsFragmentDirections.actionDetailsFragmentToApiFragment())
    }

    @Test
    fun onBackPressedTest(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<DetailsFragment>( factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.pressBack()

        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun saveTest(){
        val viewModel = ArtViewModel(FakeArtRepositoryTest())

        launchFragmentInHiltContainer<DetailsFragment>(factory = fragmentFactory){
            viewmodel = viewModel
        }
        Espresso.onView(ViewMatchers.withId(R.id.nameText)).perform(replaceText("Mona Lisa"))
        Espresso.onView(ViewMatchers.withId(R.id.artistText)).perform(replaceText("Da Vinci"))
        Espresso.onView(ViewMatchers.withId(R.id.yearText)).perform(replaceText("1500"))
        Espresso.onView(ViewMatchers.withId(R.id.saveButton)).perform(ViewActions.click())

        assertThat(viewModel.artlist.getOrAwaitValue()).contains(
            Art("Mona Lisa","Da Vinci",1500,"")
        )

    }
}