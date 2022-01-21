package tour.donnees.journal.data.repository

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.data.base.BaseRepository
import tour.donnees.journal.data.remote.api.JournalService
import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.data.local.cache.UserInfoCache
import tour.donnees.journal.data.local.dao.UserInfoDao
import tour.donnees.journal.data.mapper.UserInfoCacheMapper
import tour.donnees.journal.data.remote.response.UserInfoResponse
import tour.donnees.journal.domain.repository.SignInRepository
import java.lang.Exception
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
        private val service: JournalService,
        private val userInfoDao: UserInfoDao,
        private val mapper: UserInfoCacheMapper,
        private val sharedPreference: JournalSharedPreference
    ): BaseRepository(), SignInRepository {

    override fun getLoggedUser(username: String, password: String): Observable<Result<String>> {
        return service.token(username, password).map { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { token ->
                        sharedPreference.setToken(token.token)
                        Result.success(token.token)
                    }?: Result.failure(Exception())
                }
                else -> Result.failure(handling(response.errorBody()))
            }
        }.onErrorReturn { Result.failure(it) }
    }

    override fun getUserInfo(token: String): Observable<Result<UserInfoCache>> {
        return service.getUserInfo(token).map { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { userInfoResponse ->
                        insertUserInfo(userInfoResponse)
                        Result.success(getUserInfoById(userInfoResponse.id))
                    }?: Result.failure(Exception())
                }
                else -> Result.failure(handling(response.errorBody()))
            }
        }.onErrorReturn { Result.failure(it) }
    }


    private fun insertUserInfo(userInfoResponse: UserInfoResponse?) {
        userInfoResponse?.let { user ->
            userInfoDao.insert(mapper.mapFrom(user))
        }
    }

    private fun getUserInfoById(id: Int): UserInfoCache {
        return userInfoDao.getUserById(id)
    }
}