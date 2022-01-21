package tour.donnees.journal.data.local.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "front_page")
data class FrontPageCache(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name ="id") var id: Int,
    @ColumnInfo(name ="created_at")var createdAt: String)