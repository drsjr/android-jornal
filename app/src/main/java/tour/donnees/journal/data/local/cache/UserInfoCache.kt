package tour.donnees.journal.data.local.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
class UserInfoCache(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name ="id") var id: Int,
    @ColumnInfo(name ="email") var email: String,
    @ColumnInfo(name ="full_name") var fullName: String,
    @ColumnInfo(name ="updated_at") var updatedAt: String)