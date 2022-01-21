package tour.donnees.journal.presentation

import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.domain.usecase.*
import tour.donnees.journal.presentation.article.ArticleViewModel
import tour.donnees.journal.presentation.login.SignInViewModel
import tour.donnees.journal.presentation.main.MainViewModel
import tour.donnees.journal.presentation.section.SectionPageViewModel
import tour.donnees.journal.presentation.splash.SplashViewModel

object ViewModel {

    fun getSplashViewModel(
        sharedPreference: JournalSharedPreference
    ): SplashViewModel {
        return SplashViewModel(
            sharedPreference
        )
    }

    fun getSignInViewModel(
        sharedPreference: JournalSharedPreference,
        setSignInUseCase: SetSignInUseCase,
        getUserInfoUseCase: GetUserInfoUseCase
    ): SignInViewModel {
        return SignInViewModel(
            sharedPreference,
            setSignInUseCase,
            getUserInfoUseCase
        )
    }

    fun getMainViewModel(
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
        getFrontPageUpdateUseCase: GetFrontPageUpdateUseCase,
        getFrontPageUseCase: GetFrontPageUseCase,
        sharedPreference: JournalSharedPreference
    ): MainViewModel {
        return MainViewModel(
            getAllCategoriesUseCase,
            getFrontPageUpdateUseCase,
            getFrontPageUseCase,
            sharedPreference
        )
    }

    fun getArticleViewModel(
        getParagraphUseCase: GetParagraphUseCase,
        sharedPreference: JournalSharedPreference
    ): ArticleViewModel {
        return ArticleViewModel(
            getParagraphUseCase,
            sharedPreference
        )
    }

    fun getSectionPageViewModel(
        getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
        sharedPreference: JournalSharedPreference
    ): SectionPageViewModel {
        return SectionPageViewModel(
            getNewsByCategoryUseCase,
            sharedPreference
        )
    }
}
