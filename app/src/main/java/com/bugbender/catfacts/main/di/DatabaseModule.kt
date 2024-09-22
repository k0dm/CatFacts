package com.bugbender.catfacts.main.di

import android.content.Context
import androidx.room.Room
import com.bugbender.catfacts.favorites.data.CatFactDao
import com.bugbender.catfacts.favorites.data.CatFactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun catFactDatabase(@ApplicationContext context: Context): CatFactDatabase {
        return Room.databaseBuilder(
            context,
            CatFactDatabase::class.java,
            "favorite_cat_fact_database"
        ).build()
    }

    @Provides
    fun dao(database: CatFactDatabase): CatFactDao = database.catFactDao()
}