package tour.donnees.journal.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tour.donnees.journal.data.local.JournalDatabase
import tour.donnees.journal.data.local.dao.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideJournalDatabase(@ApplicationContext app: Context): JournalDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            JournalDatabase::class.java,
            JournalDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(database: JournalDatabase): NewsDao {
        return database.newsDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(database: JournalDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Singleton
    @Provides
    fun provideParagraphDao(database: JournalDatabase): ParagraphDao {
        return database.paragraphDao()
    }

    @Singleton
    @Provides
    fun provideFrontPageDao(database: JournalDatabase): FrontPageDao {
        return database.frontPageDao()
    }

    @Singleton
    @Provides
    fun provideNewsFromFrontPageDao(database: JournalDatabase): FrontPageWithNewsDao {
        return database.newsFromFrontPageDao()
    }

    @Singleton
    @Provides
    fun provideUserInfoDao(database: JournalDatabase): UserInfoDao {
        return database.userInfoDao()
    }
}