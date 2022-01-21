package tour.donnees.journal.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tour.donnees.journal.domain.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

   @Binds
    abstract fun provideSignInUseCase(signInUseCaseImpl: SetSignInUseCaseImpl): SetSignInUseCase

    @Binds
    abstract fun provideGetUserInfoUseCase(getUserInfoUseCaseImpl: GetUserInfoUseCaseImpl): GetUserInfoUseCase

    @Binds
    abstract fun provideGetAllCategoriesUseCase(getAllCategoriesUseCaseImpl: GetAllCategoriesUseCaseImpl): GetAllCategoriesUseCase

    @Binds
    abstract fun provideGetFrontPageUpdateUseCase(getFrontPageUpdateUseCaseImpl: GetFrontPageUpdateUseCaseImpl): GetFrontPageUpdateUseCase

    @Binds
    abstract fun provideGetFrontPageUseCase(getFrontPageUseCaseImpl: GetFrontPageUseCaseImpl): GetFrontPageUseCase

    @Binds
    abstract fun provideGetNewsByCategoryUseCase(getNewsByCategoryUseCaseImpl: GetNewsByCategoryUseCaseImpl): GetNewsByCategoryUseCase

    @Binds
    abstract fun provideGetParagraphUseCase(getParagraphUseCaseImpl: GetParagraphUseCaseImpl): GetParagraphUseCase

    @Binds
    abstract fun provideGetCategoryUseCase(getCategoryUseCaseImpl: GetCategoryUseCaseImpl): GetCategoryUseCase

}