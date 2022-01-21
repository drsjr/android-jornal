package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.modal.UserInfo

interface GetUserInfoUseCase {
    fun invoke(token: String): Observable<Result<UserInfo>>
}