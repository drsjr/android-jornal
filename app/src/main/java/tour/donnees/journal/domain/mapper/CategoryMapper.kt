package tour.donnees.journal.domain.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.CategoryCache
import tour.donnees.journal.data.local.cache.NewsCache
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.modal.News
import javax.inject.Inject

class CategoryMapper @Inject constructor():
    BaseMapper<CategoryCache, Category> {

    override fun mapFrom(entity: CategoryCache): Category {
        return Category(
            id = entity.id,
            name = entity.name,
            path = entity.path)
    }

    override fun mapTo(entity: Category): CategoryCache {
        return CategoryCache(
            id = entity.id,
            name = entity.name,
            path = entity.path,
            code = 0,
            disabled = false)
    }

    fun mapFromList(entities: List<CategoryCache>): List<Category> {
        return entities.map { mapFrom(it) }
    }
}