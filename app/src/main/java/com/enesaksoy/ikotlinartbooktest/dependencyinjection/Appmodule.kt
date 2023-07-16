package com.enesaksoy.ikotlinartbooktest.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enesaksoy.ikotlinartbooktest.R
import com.enesaksoy.ikotlinartbooktest.api.RetrofitApi
import com.enesaksoy.ikotlinartbooktest.repo.ArtRepository
import com.enesaksoy.ikotlinartbooktest.repo.ArtRepositoryInterface
import com.enesaksoy.ikotlinartbooktest.roomdb.ArtDao
import com.enesaksoy.ikotlinartbooktest.roomdb.ArtDatabase
import com.enesaksoy.ikotlinartbooktest.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Appmodule {

    @Provides
    @Singleton
    fun injectdatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context
        ,ArtDatabase::class.java,"ArtDatabase"
    ).build()

    @Provides
    @Singleton
    fun injectDao(database : ArtDatabase) = database.artDao()

    @Provides
    @Singleton
    fun injectRetrofit() : RetrofitApi{
       return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitApi::class.java)
    }

    @Provides
    @Singleton
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )

    @Provides
    @Singleton
    fun repoInject(dao : ArtDao,retrofit : RetrofitApi) : ArtRepositoryInterface {
        return ArtRepository(dao,retrofit)
    }

}