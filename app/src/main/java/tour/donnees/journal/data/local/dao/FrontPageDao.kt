package tour.donnees.journal.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import tour.donnees.journal.data.local.cache.FrontPageCache

@Dao
interface FrontPageDao: BaseDao<FrontPageCache> {

    @Query("SELECT * FROM front_page ORDER BY id DESC LIMIT 1")
    fun getLastFrontPage(): FrontPageCache
}
