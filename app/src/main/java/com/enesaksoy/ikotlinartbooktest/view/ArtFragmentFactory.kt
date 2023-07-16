package com.enesaksoy.ikotlinartbooktest.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.enesaksoy.ikotlinartbooktest.adapter.ArtAdapter
import com.enesaksoy.ikotlinartbooktest.adapter.ImageAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(private val glide : RequestManager,
                                             private val imageAdapter: ImageAdapter,
                                             private val artAdapter: ArtAdapter
                                             ): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
       return when(className){
           ArtFragment::class.java.name -> ArtFragment(artAdapter)
           ApiFragment::class.java.name -> ApiFragment(imageAdapter)
            DetailsFragment::class.java.name -> DetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}