package tour.donnees.journal.data.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.FrontPageCache
import tour.donnees.journal.data.remote.response.FrontPageResponse
import javax.inject.Inject

class FrontPageCacheMapper @Inject
    constructor(): BaseMapper<FrontPageResponse, FrontPageCache> {

    override fun mapFrom(entity: FrontPageResponse): FrontPageCache {
        return FrontPageCache(
            id = entity.id,
            createdAt = entity.createdAt)
    }

    override fun mapTo(entity: FrontPageCache): FrontPageResponse {
        return FrontPageResponse(
                id = entity.id,
                createdAt = entity.createdAt)
    }
}