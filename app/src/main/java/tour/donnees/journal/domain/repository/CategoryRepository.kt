package tour.donnees.journal.domain.repository

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.data.local.cache.CategoryCache
import tour.donnees.journal.domain.modal.Category

interface CategoryRepository {

    fun getAllCategories(token: String): Observable<Result<List<CategoryCache>>>

    fun getCategoriesById(token: String, categoryId: Int): Observable<Result<CategoryCache>>
}