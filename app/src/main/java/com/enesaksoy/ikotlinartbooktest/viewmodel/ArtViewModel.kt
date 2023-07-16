package com.enesaksoy.ikotlinartbooktest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesaksoy.ikotlinartbooktest.model.ImageResponse
import com.enesaksoy.ikotlinartbooktest.repo.ArtRepositoryInterface
import com.enesaksoy.ikotlinartbooktest.roomdb.Art
import com.enesaksoy.ikotlinartbooktest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ArtViewModel @Inject constructor(val repository : ArtRepositoryInterface) : ViewModel() {

    //ArtFragment işlemleri

    val artlist = repository.getArt()

    //apifragment işlemleri

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>>
    get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String>
    get() = selectedImage

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
        get() = insertArtMsg

    //Solving the navigation bug
    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setselectedImage(url : String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art : Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun insertArt(art : Art) = viewModelScope.launch {
        repository.addArt(art)
    }

    fun searchForImage(searchString : String){
        if(searchString.isEmpty()){
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }

    fun makeArt(name : String, artistname : String, year : String){
        if(name.isEmpty() ||artistname.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("lütfen sekmeleri doldurun",null))
            return
        }
        val yearInt = try {
            year.toInt()
        }catch (e : Exception){
            insertArtMsg.postValue(Resource.error("lütfen yılı uygun giriniz",null))
            return
        }
        val art = Art(name,artistname,yearInt,selectedImage.value ?: "")
        insertArt(art)
        setselectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }
}