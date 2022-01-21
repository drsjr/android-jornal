package tour.donnees.journal.domain.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.NewsCache
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.modal.News
import javax.inject.Inject

class NewsMapper @Inject
    constructor(): BaseMapper<NewsCache, News> {

    override fun mapFrom(entity: NewsCache): News {
        return News(
            id = entity.id,
            url = entity.url,
            title = entity.title,
            subtitle = entity.subtitle,
            createdAt = entity.createdAt,
            imageUrl = entity.imageUrl,
            category = Category(0, "", "")
        )
    }

    override fun mapTo(entity: News): NewsCache {
        return NewsCache(
            id = entity.id,
            url = entity.url,
            title = entity.title,
            subtitle = entity.subtitle,
            createdAt = entity.createdAt,
            imageUrl = entity.imageUrl,
            categoryId = 0)
    }

    fun mapFromList(entities: List<NewsCache>): List<News> {
        return entities.map { mapFrom(it) }
    }

    fun mapToList(entities: List<News>): List<NewsCache> {
        return entities.map { mapTo(it) }
    }
}