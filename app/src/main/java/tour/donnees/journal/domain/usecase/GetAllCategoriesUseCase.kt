package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.modal.Category

interface GetAllCategoriesUseCase {
    fun invoke(token: String): Observable<Result<List<Category>>>
}