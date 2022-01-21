package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.core.BaseUseCase
import tour.donnees.journal.domain.mapper.UserInfoMapper
import tour.donnees.journal.domain.modal.UserInfo
import tour.donnees.journal.domain.repository.SignInRepository
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject
constructor(
    private val repository: SignInRepository,
    private val mapper: UserInfoMapper
): BaseUseCase(), GetUserInfoUseCase {
    override fun invoke(token: String): Observable<Result<UserInfo>> {
        return super.invoke(repository.getUserInfo(token).map { result ->
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