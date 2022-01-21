package tour.donnees.journal.presentation.article

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.functions.Consumer
import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.modal.Paragraph
import tour.donnees.journal.domain.usecase.GetCategoryUseCase
import tour.donnees.journal.domain.usecase.GetParagraphUseCase
import tour.donnees.journal.presentation.base.BaseViewModel
import tour.donnees.journal.presentation.base.error
import tour.donnees.journal.presentation.base.running
import tour.donnees.journal.presentation.base.success
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getParagraphUseCase: GetParagraphUseCase,
    sharedPreference: JournalSharedPreference
): BaseViewModel(sharedPreference) {

    var paragraphs = MutableLiveData<List<Paragraph>>()
    var category = MutableLiveData<Category>()

    fun getParagraphs(articleId: Int){
        networkState.running()
        getParagraphUseCase
            .invoke(getToken(), articleId)
            .subscribe(getParagraphsConsumer())
    }

    private fun getParagraphsConsumer(): Consumer<Result<List<Paragraph>>> {
        return Consumer<Result<List<Paragraph>>> { result ->
            result.fold({
                paragraphs.postValue(it)
                networkState.success()
            }, {
                throwError(it)
                networkState.error()
            })
        }
    }

}