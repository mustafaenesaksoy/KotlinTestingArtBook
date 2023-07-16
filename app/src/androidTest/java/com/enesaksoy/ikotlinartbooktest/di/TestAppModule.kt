package com.enesaksoy.ikotlinartbooktest.di

import android.content.Context
import androidx.room.Room
import com.enesaksoy.ikotlinartbooktest.roomdb.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    @Named("TestRoom")
    fun injectinMemoryDatabase(@ApplicationContext context: Context) = Room.inMemoryDatabaseBuilder(context
        ,ArtDatabase::class.java
    ).allowMainThreadQueries().build()
}