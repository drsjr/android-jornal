package tour.donnees.journal.data.repository

import tour.donnees.journal.base.JournalSharedPreferenceMock
import tour.donnees.journal.data.local.dao.*
import tour.donnees.journal.data.mapper.*
import tour.donnees.journal.data.remote.api.JournalService
import tour.donnees.journal.domain.mapper.*

object Repositories {

    var sharedPreferenceMock: JournalSharedPreferenceMock = JournalSharedPreferenceMock()

    fun getCategoryRepository(
        service: JournalService,
        categoryDao: CategoryDao
    ): CategoryRepositoryImpl {
        return CategoryRepositoryImpl(service, categoryDao, CategoryCacheMapper())
    }

    fun getFrontPageRepository(
        service: JournalService,
        frontPageDao: FrontPageDao,
        newsDao: NewsDao,
        frontPageWithNewsDao: FrontPageWithNewsDao
    ): FrontPageRepositoryImpl {
        return FrontPageRepositoryImpl(
            service,
            frontPageDao,
            newsDao,
            frontPageWithNewsDao,
            FrontPageCacheMapper(),
            FrontPageWithNewsCacheMapper(),
            NewsCacheMapper())
    }

    fun getSignInRepository(
        service: JournalService,
        userInfoDao: UserInfoDao
    ): SignInRepositoryImpl {
        return SignInRepositoryImpl(
            service,
            userInfoDao,
            UserInfoCacheMapper(),
            sharedPreferenceMock)
    }

    fun getNewsRepository(
        service: JournalService,
        newsDao: NewsDao,
        paragraphDao: ParagraphDao
    ): NewsRepositoryImpl {
        return NewsRepositoryImpl(
            service,
            NewsCacheMapper(),
            ParagraphCacheMapper(),
            newsDao,
            paragraphDao)
    }

}