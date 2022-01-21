package tour.donnees.journal.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tour.donnees.journal.data.repository.CategoryRepositoryImpl
import tour.donnees.journal.data.repository.FrontPageRepositoryImpl
import tour.donnees.journal.data.repository.SignInRepositoryImpl
import tour.donnees.journal.domain.repository.NewsRepository
import tour.donnees.journal.data.repository.NewsRepositoryImpl
import tour.donnees.journal.domain.repository.CategoryRepository
import tour.donnees.journal.domain.repository.FrontPageRepository
import tour.donnees.journal.domain.repository.SignInRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

    @Binds
    abstract fun provideLoggedInRepository(LoggedInRepositoryImpl: SignInRepositoryImpl): SignInRepository

    @Binds
    abstract fun provideFrontPageRepository(frontPageRepositoryImpl: FrontPageRepositoryImpl): FrontPageRepository

    @Binds
    abstract fun provideCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

}