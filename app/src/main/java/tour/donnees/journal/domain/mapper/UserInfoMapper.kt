package tour.donnees.journal.domain.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.UserInfoCache
import tour.donnees.journal.domain.modal.UserInfo
import javax.inject.Inject

class UserInfoMapper @Inject constructor():
    BaseMapper<UserInfoCache, UserInfo> {

    override fun mapFrom(entity: UserInfoCache): UserInfo {
        return UserInfo(
            id = entity.id,
            email = entity.email,
            fullName = entity.fullName,
            updatedAt = entity.updatedAt)
    }

    override fun mapTo(entity: UserInfo): UserInfoCache {
        return UserInfoCache(
            id = entity.id,
            email = entity.email,
            fullName = entity.fullName,
            updatedAt = entity.updatedAt)
    }
}