package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.modal.News

interface GetNewsByCategoryUseCase {
    fun invoke(token: String, categoryId: Int, offset: Int, limit: Int): Observable<Result<List<News>>>
}