package tour.donnees.journal.data.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.CategoryCache
import tour.donnees.journal.data.local.cache.UserInfoCache
import tour.donnees.journal.data.remote.response.CategoryResponse
import tour.donnees.journal.data.remote.response.UserInfoResponse
import javax.inject.Inject

class UserInfoCacheMapper @Inject constructor():
    BaseMapper<UserInfoResponse, UserInfoCache> {

    override fun mapFrom(entity: UserInfoResponse): UserInfoCache {
        return UserInfoCache(
            id = entity.id,
            email = entity.email,
            fullName = entity.fullName,
            updatedAt = entity.updatedAt)
    }

    override fun mapTo(entity: UserInfoCache): UserInfoResponse {
        return UserInfoResponse(
            id = entity.id,
            email = entity.email,
            fullName = entity.fullName,
            updatedAt = entity.updatedAt)
    }
}