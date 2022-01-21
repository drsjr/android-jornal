package tour.donnees.journal.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import tour.donnees.journal.data.local.cache.UserInfoCache

@Dao
interface UserInfoDao: BaseDao<UserInfoCache> {

    @Query("SELECT * FROM user_info WHERE id = :userId LIMIT 1")
    fun getUserById(userId: Int): UserInfoCache
}
