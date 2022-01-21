package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable

interface SetSignInUseCase {
    fun invoke(username: String, password: String): Observable<Result<String>>
}