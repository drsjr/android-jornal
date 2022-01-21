package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.core.BaseUseCase
import tour.donnees.journal.domain.repository.SignInRepository
import javax.inject.Inject

class SetSignInUseCaseImpl @Inject
constructor(private val repository: SignInRepository): BaseUseCase(), SetSignInUseCase {
    override fun invoke(username: String, password: String): Observable<Result<String>> {
        return super.invoke(repository.getLoggedUser(username, password))
    }
}