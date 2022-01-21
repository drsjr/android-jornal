package tour.donnees.journal.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.data.local.cache.CategoryCache
import tour.donnees.journal.data.local.cache.NewsCache

@Dao
interface CategoryDao: BaseDao<CategoryCache> {

    @Query("SELECT * FROM category WHERE disabled = :disabled")
    fun getAllCategories(disabled: Boolean = false): List<CategoryCache>

    @Query("SELECT * FROM category WHERE id = :categoryId")
    fun getCategoryById(categoryId: Int): CategoryCache
}
