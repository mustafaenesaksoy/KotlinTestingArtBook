package com.enesaksoy.ikotlinartbooktest.repo

import androidx.lifecycle.LiveData
import com.enesaksoy.ikotlinartbooktest.api.RetrofitApi
import com.enesaksoy.ikotlinartbooktest.model.ImageResponse
import com.enesaksoy.ikotlinartbooktest.roomdb.Art
import com.enesaksoy.ikotlinartbooktest.roomdb.ArtDao
import com.enesaksoy.ikotlinartbooktest.util.Resource
import javax.inject.Inject

class ArtRepository@Inject constructor(private val artDao : ArtDao,private var retrofitApi: RetrofitApi) : ArtRepositoryInterface {
    override suspend fun addArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try{
            val response = retrofitApi.imageSearch(imageString)
            if(response.isSuccessful){
                response.body()?.let {
                   return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : Exception){
            Resource.error("No data!",null)
        }
    }
}