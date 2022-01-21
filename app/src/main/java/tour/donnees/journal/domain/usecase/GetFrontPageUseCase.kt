package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.modal.FrontPage
import tour.donnees.journal.domain.modal.FrontPageNews

interface GetFrontPageUseCase {
    fun invoke(token: String): Observable<Result<List<FrontPageNews>>>
}