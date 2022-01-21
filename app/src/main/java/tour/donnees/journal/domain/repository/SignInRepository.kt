package tour.donnees.journal.domain.repository

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.data.local.cache.UserInfoCache

interface SignInRepository {

    fun getLoggedUser(username: String, password: String): Observable<Result<String>>

    fun getUserInfo(token: String): Observable<Result<UserInfoCache>>
}