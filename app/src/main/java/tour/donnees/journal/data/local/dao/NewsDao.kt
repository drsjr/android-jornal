package tour.donnees.journal.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import tour.donnees.journal.data.local.cache.NewsAndCategoryCache
import tour.donnees.journal.data.local.cache.NewsCache

@Dao
interface NewsDao: BaseDao<NewsCache> {
    @Query("SELECT * FROM news")
    fun getAllNews(): List<NewsCache>

    @Transaction
    @Query("SELECT * FROM news")
    fun getNewsAndCategoryByNewsId(): List<NewsAndCategoryCache>

    @Transaction
    @Query("SELECT * FROM news WHERE id = :newsId")
    fun getNewsAndCategoryByNewsId(newsId: Int): NewsAndCategoryCache

    @Query("SELECT * FROM news WHERE id = :newsId")
    fun getNewsByArticleId(newsId: Int): NewsCache

    @Transaction
    @Query("SELECT * FROM news WHERE id = :newsId")
    fun getNewsAndCategoryByArticleId(newsId: Int): NewsAndCategoryCache

    @Transaction
    @Query("SELECT * FROM news WHERE category_id = :categoryId ORDER BY id DESC")
    fun getAllNewsAndCategoryByCategoryId(categoryId: Int): List<NewsAndCategoryCache>

}
