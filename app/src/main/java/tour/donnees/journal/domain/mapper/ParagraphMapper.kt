package tour.donnees.journal.domain.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.NewsCache
import tour.donnees.journal.data.local.cache.ParagraphCache
import tour.donnees.journal.data.remote.response.NewsResponse
import tour.donnees.journal.data.remote.response.ParagraphResponse
import tour.donnees.journal.domain.modal.Paragraph
import javax.inject.Inject

class ParagraphMapper @Inject
    constructor(): BaseMapper<ParagraphCache, Paragraph> {

    var articleId: Int = 0

    override fun mapFrom(entity: ParagraphCache): Paragraph {
        return Paragraph(
            id = entity.id,
            order = entity.order,
            paragraph = entity.paragraph)
    }

    override fun mapTo(entity: Paragraph): ParagraphCache {
        return ParagraphCache(
            id = entity.id,
            order = entity.order,
            paragraph = entity.paragraph,
            articleId = articleId)
    }

    fun mapFromList(entities: List<ParagraphCache>): List<Paragraph> {
        return entities.map { mapFrom(it) }
    }
}