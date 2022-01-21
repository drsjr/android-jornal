package tour.donnees.journal.domain.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.FrontPageCache
import tour.donnees.journal.domain.modal.FrontPage
import javax.inject.Inject

class FrontPageMapper @Inject
    constructor(): BaseMapper<FrontPageCache, FrontPage> {

    override fun mapFrom(entity: FrontPageCache): FrontPage {
        return FrontPage(
            id = entity.id,
            createdAt = entity.createdAt
        )
    }

    override fun mapTo(entity: FrontPage): FrontPageCache {
        return FrontPageCache(
            id = entity.id,
            createdAt = entity.createdAt
        )
    }
}