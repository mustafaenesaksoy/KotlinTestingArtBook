package com.enesaksoy.ikotlinartbooktest.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enesaksoy.ikotlinartbooktest.model.ImageResponse
import com.enesaksoy.ikotlinartbooktest.roomdb.Art
import com.enesaksoy.ikotlinartbooktest.util.Resource
import com.enesaksoy.ikotlinartbooktest.util.Status

class FakeArtRepository : ArtRepositoryInterface{

    private val artList = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(artList)
    override suspend fun addArt(art: Art) {
        artList.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        artList.remove(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource(Status.SUCCESS,ImageResponse(listOf(),0,0),"")
    }

    private fun refreshData(){
        artsLiveData.postValue(artList)
    }
}