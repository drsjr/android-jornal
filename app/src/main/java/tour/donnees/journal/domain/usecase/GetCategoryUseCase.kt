package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.modal.Category

interface GetCategoryUseCase {
    fun invoke(token: String, categoryId: Int): Observable<Result<Category>>
}