package tour.donnees.journal.data.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.NewsCache
import tour.donnees.journal.data.remote.response.NewsResponse
import javax.inject.Inject

class NewsCacheMapper @Inject
    constructor(): BaseMapper<NewsResponse, NewsCache> {

    override fun mapFrom(entity: NewsResponse): NewsCache {
        return NewsCache(
            id = entity.id,
            url = entity.url,
            title = entity.title,
            subtitle = entity.subtitle,
            createdAt = entity.createdAt,
            imageUrl = entity.imageUrl,
            categoryId = entity.categoryId)
    }

    override fun mapTo(entity: NewsCache): NewsResponse {
        return NewsResponse(
            id = entity.id,
            url = entity.url,
            title = entity.title,
            subtitle = entity.subtitle,
            createdAt = entity.createdAt,
            imageUrl = entity.imageUrl,
            categoryId = entity.categoryId)
    }

    fun mapFromList(entities: List<NewsResponse>): List<NewsCache> {
        return entities.map { mapFrom(it) }
    }

    fun mapToList(entities: List<NewsCache>): List<NewsResponse> {
        return entities.map { mapTo(it) }
    }
}