package tour.donnees.journal.data.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.NewsCache
import tour.donnees.journal.data.local.cache.ParagraphCache
import tour.donnees.journal.data.remote.response.NewsResponse
import tour.donnees.journal.data.remote.response.ParagraphResponse
import javax.inject.Inject

class ParagraphCacheMapper @Inject
    constructor(): BaseMapper<ParagraphResponse, ParagraphCache> {

    override fun mapFrom(entity: ParagraphResponse): ParagraphCache {
        return ParagraphCache(
            id = entity.id,
            order = entity.order,
            paragraph = entity.paragraph,
            articleId = entity.articleId)
    }

    override fun mapTo(entity: ParagraphCache): ParagraphResponse {
        return ParagraphResponse(
            id = entity.id,
            order = entity.order,
            paragraph = entity.paragraph,
            articleId = entity.articleId)
    }

    fun mapFromList(entities: List<ParagraphResponse>): List<ParagraphCache> {
        return entities.map { mapFrom(it) }
    }
}