package tour.donnees.journal.presentation.main


import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.functions.Consumer
import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.modal.FrontPage
import tour.donnees.journal.domain.modal.FrontPageNews
import tour.donnees.journal.domain.usecase.GetAllCategoriesUseCase
import tour.donnees.journal.domain.usecase.GetFrontPageUpdateUseCase
import tour.donnees.journal.domain.usecase.GetFrontPageUseCase
import tour.donnees.journal.presentation.base.BaseViewModel
import tour.donnees.journal.presentation.base.error
import tour.donnees.journal.presentation.base.running
import tour.donnees.journal.presentation.base.success
import tour.donnees.journal.utils.DateUtils
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getFrontPageUpdateUseCase: GetFrontPageUpdateUseCase,
    private val getFrontPageUseCase: GetFrontPageUseCase,
    sharedPreference: JournalSharedPreference
) : BaseViewModel(sharedPreference) {

    private val dateUtils: DateUtils by lazy { DateUtils() }
    var mapCategories = mutableMapOf<String, Category>()
    var frontPage = MutableLiveData<FrontPage>()
    var frontPageNews = MutableLiveData<List<FrontPageNews>>()
    private var hasNext = true

    fun getAllCategories() {
        networkState.running()
        getAllCategoriesUseCase.invoke(getToken())
            .subscribe(getAllCategoriesConsumer())
    }

    fun getFrontPageUpdate() {
        networkState.running()
        getFrontPageUpdateUseCase.invoke(getToken())
            .subscribe(getFrontPageUpdateConsumer())
    }

    fun getFrontPageNews() {
        networkState.running()
        getFrontPageUseCase.invoke(getToken())
            .subscribe(getFrontPageNewsConsumer())
    }

    private fun getAllCategoriesConsumer(): Consumer<Result<List<Category>>> {
        return Consumer<Result<List<Category>>> { result ->
            result.fold({ list ->
               list.map { c -> mapCategories[c.name] = c }
                networkState.success()
            }, {
                throwError(it)
                networkState.error()
            })
        }
    }

    private fun getFrontPageUpdateConsumer(): Consumer<Result<FrontPage>> {
        return Consumer { result ->
            result.fold({ response ->
                frontPage.value?.createdAt?.let {
                    if (dateUtils.compareDate(it, response.createdAt)) {
                        getFrontPageNews()
                        this.frontPage.postValue(response)
                    }
                } ?: run {
                    this.frontPage.postValue(response)
                    getFrontPageNews()
                }
                networkState.success()
            }, {
                throwError(it)
                networkState.error()
            })
        }
    }

    private fun getFrontPageNewsConsumer(): Consumer<Result<List<FrontPageNews>>> {
        return Consumer { result ->
            result.fold({
                frontPageNews.postValue(it)
                networkState.success()
            }, {
                throwError(it)
                networkState.error()
            })
        }
    }

    companion object {
        const val RESPONSE_GET_ALL_CATEGORY_SUCCESS = "RESPONSE_GET_ALL_CATEGORY_SUCCESS"
        const val RESPONSE_FRONT_PAGE_UPDATE_SUCCESS = "RESPONSE_FRONT_PAGE_UPDATE_SUCCESS"
        const val RESPONSE_FRONT_PAGE_SUCCESS = "RESPONSE_FRONT_PAGE_SUCCESS"
        const val RESPONSE_FRONT_PAGE_NEWS_SUCCESS = "RESPONSE_FRONT_PAGE_NEWS_SUCCESS"
        const val RESPONSE_ERROR = ""
    }
}
