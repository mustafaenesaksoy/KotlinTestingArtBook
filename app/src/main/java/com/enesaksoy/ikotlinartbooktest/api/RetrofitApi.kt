package com.enesaksoy.ikotlinartbooktest.api

import com.enesaksoy.ikotlinartbooktest.model.ImageResponse
import com.enesaksoy.ikotlinartbooktest.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery : String,
        @Query("key") apiKey  :String = API_KEY
    ) : Response<ImageResponse>
}