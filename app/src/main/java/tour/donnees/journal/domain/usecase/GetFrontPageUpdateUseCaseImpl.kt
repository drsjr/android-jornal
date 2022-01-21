package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.core.BaseUseCase
import tour.donnees.journal.domain.mapper.FrontPageMapper
import tour.donnees.journal.domain.modal.FrontPage
import tour.donnees.journal.domain.repository.FrontPageRepository
import javax.inject.Inject

class GetFrontPageUpdateUseCaseImpl @Inject constructor(
    private val repository: FrontPageRepository,
    private val mapper: FrontPageMapper
): BaseUseCase(),  GetFrontPageUpdateUseCase {
    override fun invoke(token: String): Observable<Result<FrontPage>> {
        return super.invoke(repository.getFrontPageUpdate(token).map { result ->
            result.fold({
                Result.success(mapper.mapFrom(it))
            }, {
                Result.failure(it)
            })
        }.onErrorReturn {
            Result.failure(it)
        })
    }
}