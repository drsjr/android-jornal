package tour.donnees.journal.data.local.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryCache(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name ="id") var id: Int,
    @ColumnInfo(name ="name") var name: String,
    @ColumnInfo(name ="path") var path: String,
    @ColumnInfo(name ="code") var code: Int,
    @ColumnInfo(name ="disabled") var disabled: Boolean)