package com.enesaksoy.ikotlinartbooktest.repo

import androidx.lifecycle.LiveData
import com.enesaksoy.ikotlinartbooktest.model.ImageResponse
import com.enesaksoy.ikotlinartbooktest.roomdb.Art
import com.enesaksoy.ikotlinartbooktest.util.Resource

interface ArtRepositoryInterface {

    suspend fun addArt(art : Art)

    suspend fun deleteArt(art : Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>


}