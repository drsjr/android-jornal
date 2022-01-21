package tour.donnees.journal.data.repository

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.data.base.BaseRepository
import tour.donnees.journal.data.local.cache.CategoryCache
import tour.donnees.journal.data.local.dao.CategoryDao
import tour.donnees.journal.data.mapper.CategoryCacheMapper
import tour.donnees.journal.data.remote.api.JournalService
import tour.donnees.journal.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject
    constructor(
        private val service: JournalService,
        private val categoryDao: CategoryDao,
        private val cacheMapper: CategoryCacheMapper): BaseRepository(), CategoryRepository {

    override fun getAllCategories(token: String): Observable<Result<List<CategoryCache>>> {
        return service.getAllCategories(token).map { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { body ->
                        val categoryCacheList = cacheMapper.mapFromList(body)
                        insertCategories(categoryCacheList)
                        Result.success(getLocalCategories())
                    } ?: Result.failure(Exception())
                }
                else -> Result.failure(handling(response.errorBody()))
            }
        }.onErrorReturn { Result.failure(it) }
    }

    override fun getCategoriesById(token: String, categoryId: Int): Observable<Result<CategoryCache>> {
        return service.getCategoryById(token, categoryId).map { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { body ->
                        insertCategories(listOf(cacheMapper.mapFrom(body)))
                        Result.success(getLocalCategoryById(categoryId))
                    } ?: Result.failure(Exception())
                }
                else ->  Result.failure(handling(response.errorBody()))
            }
        }.onErrorReturn { Result.failure(it) }
    }

    private fun insertCategories(response: List<CategoryCache>) {
        response.map {
            categoryDao.insert(it)
        }
    }

    private fun getLocalCategories(): List<CategoryCache> {
        return categoryDao.getAllCategories(false)
    }

    private fun getLocalCategoryById(categoryId: Int): CategoryCache {
        return  categoryDao.getCategoryById(categoryId)
    }
}