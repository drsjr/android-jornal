package tour.donnees.journal.domain.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.NewsAndCategoryCache
import tour.donnees.journal.domain.modal.News
import javax.inject.Inject

class NewsAndCategoryMapper @Inject constructor(
        private val categoryMapper: CategoryMapper,
        private val newsMapper: NewsMapper
        ): BaseMapper<NewsAndCategoryCache, News> {

    override fun mapFrom(entity: NewsAndCategoryCache): News {
        return News(
            id = entity.news.id,
            url = entity.news.url,
            title = entity.news.title,
            subtitle = entity.news.subtitle,
            createdAt = entity.news.createdAt,
            imageUrl = entity.news.imageUrl,
            category = categoryMapper.mapFrom(entity.category))
    }

    override fun mapTo(entity: News): NewsAndCategoryCache {
        return NewsAndCategoryCache(
            news = newsMapper.mapTo(entity),
            category = categoryMapper.mapTo(entity.category))
    }

    fun mapFromList(entities: List<NewsAndCategoryCache>): List<News> {
        return entities.map { mapFrom(it) }
    }
}