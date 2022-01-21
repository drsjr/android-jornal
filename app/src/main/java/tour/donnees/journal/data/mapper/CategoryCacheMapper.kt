package tour.donnees.journal.data.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.CategoryCache
import tour.donnees.journal.data.remote.response.CategoryResponse
import javax.inject.Inject

class CategoryCacheMapper @Inject constructor():
    BaseMapper<CategoryResponse, CategoryCache> {

    override fun mapFrom(entity: CategoryResponse): CategoryCache {
        return CategoryCache(
            id = entity.id,
            name = entity.name,
            path = entity.path,
            code = entity.code,
            disabled = entity.disabled)
    }

    override fun mapTo(entity: CategoryCache): CategoryResponse {
        return CategoryResponse(
            id = entity.id,
            name = entity.name,
            path = entity.path,
            code = entity.code,
            disabled = entity.disabled)
    }

    fun mapFromList(entities: List<CategoryResponse>): List<CategoryCache> {
        return entities.map { mapFrom(it) }
    }
}