package tour.donnees.journal.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import tour.donnees.journal.data.local.cache.FrontPageWithNewsCache
import tour.donnees.journal.data.local.cache.NewsAndCategoryCache
import tour.donnees.journal.data.local.cache.NewsFromFrontPageCache

@Dao
interface FrontPageWithNewsDao: BaseDao<FrontPageWithNewsCache> {

    @Query("SELECT * FROM front_page_news WHERE front_page_id = :frontPageId ORDER BY article_id DESC")
    fun getFrontPageNews(frontPageId: Int): List<FrontPageWithNewsCache>

    @Transaction
    @Query("SELECT * FROM front_page_news WHERE front_page_id = :frontPageId ORDER BY article_id DESC")
    fun getNewsFromFrontPage(frontPageId: Int): List<NewsFromFrontPageCache>

}