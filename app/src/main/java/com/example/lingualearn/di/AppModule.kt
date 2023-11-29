package com.example.lingualearn.di

import android.app.Application
import androidx.room.Room
import com.example.lingualearn.data.DayDao
import com.example.lingualearn.data.DayDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): DayDatabase {
        return Room.databaseBuilder(
            app,
            DayDatabase::class.java,
            "day_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideDao(dayDb: DayDatabase): DayDao {
        return dayDb.dao
    }
}