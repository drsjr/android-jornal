package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.modal.FrontPage

interface GetFrontPageUpdateUseCase {
    fun invoke(token: String): Observable<Result<FrontPage>>
}