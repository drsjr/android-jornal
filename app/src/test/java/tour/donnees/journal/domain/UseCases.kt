package tour.donnees.journal.domain

import tour.donnees.journal.domain.mapper.*
import tour.donnees.journal.domain.repository.CategoryRepository
import tour.donnees.journal.domain.repository.FrontPageRepository
import tour.donnees.journal.domain.repository.NewsRepository
import tour.donnees.journal.domain.repository.SignInRepository
import tour.donnees.journal.domain.usecase.*

object UseCases {

    fun getAllCategoriesUseCase(categoryRepository: CategoryRepository): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCaseImpl(
            categoryRepository,
            CategoryMapper()
        )
    }

    fun getCategoryUseCase(categoryRepository: CategoryRepository): GetCategoryUseCase {
        return GetCategoryUseCaseImpl(
            categoryRepository,
            CategoryMapper()
        )
    }

    fun getFrontPageUpdateUseCase(repository: FrontPageRepository): GetFrontPageUpdateUseCase {
        return GetFrontPageUpdateUseCaseImpl(
            repository,
            FrontPageMapper()
        )
    }

    fun getFrontPageUseCase(frontPageRepository: FrontPageRepository, newsRepository: NewsRepository): GetFrontPageUseCase {
        val mapper = NewsMapper()
        return GetFrontPageUseCaseImpl(
            frontPageRepository,
            newsRepository,
            FrontPageNewsMapper(mapper),
            NewsAndCategoryMapper(CategoryMapper(), mapper)
        )
    }

    fun getNewsByCategoryUseCase(repository: NewsRepository): GetNewsByCategoryUseCase {
        return GetNewsByCategoryUseCaseImpl(
            repository,
            NewsAndCategoryMapper(CategoryMapper(), NewsMapper())
        )
    }

    fun getParagraphUseCase(newsRepository: NewsRepository): GetParagraphUseCase {
        return GetParagraphUseCaseImpl(
            newsRepository,
            ParagraphMapper()
        )
    }

    fun getUserInfoUseCase(signInRepository: SignInRepository): GetUserInfoUseCase {
        return GetUserInfoUseCaseImpl(
            signInRepository,
            UserInfoMapper()
        )
    }

    fun setSignInUseCase(signInRepository: SignInRepository): SetSignInUseCase {
        return SetSignInUseCaseImpl(
            signInRepository
        )
    }
}