package tour.donnees.journal.presentation.section

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.functions.Consumer
import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.domain.usecase.GetNewsByCategoryUseCase
import tour.donnees.journal.presentation.base.BaseViewModel
import tour.donnees.journal.presentation.base.error
import tour.donnees.journal.presentation.base.running
import tour.donnees.journal.presentation.base.success
import javax.inject.Inject

@HiltViewModel
class SectionPageViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
    sharedPreference: JournalSharedPreference,
): BaseViewModel(sharedPreference) {

   var news = MutableLiveData<List<News>>()

    fun getNewsByCategory(categoryId: Int, offset: Int = 0, limit: Int = 10) {
        networkState.running()
        getNewsByCategoryUseCase.invoke(getToken(), categoryId, offset, limit)
            .subscribe(consumer())
    }

    private fun consumer(): Consumer<Result<List<News>>> {
        return Consumer { result ->
            result.fold({
                news.postValue(it)
                networkState.success()
            }, {
                throwError(it)
                networkState.error()
            })
        }
    }
}