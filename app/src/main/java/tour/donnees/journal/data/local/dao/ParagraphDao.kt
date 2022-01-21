package tour.donnees.journal.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import tour.donnees.journal.data.local.cache.ParagraphCache

@Dao
interface ParagraphDao: BaseDao<ParagraphCache> {
    @Query("SELECT * FROM paragraph WHERE article_id = :articleId")
    fun getParagraphByArticleId(articleId: Int): List<ParagraphCache>
}