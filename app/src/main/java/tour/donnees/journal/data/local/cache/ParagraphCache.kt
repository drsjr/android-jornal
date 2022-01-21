package tour.donnees.journal.data.local.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paragraph")
data class ParagraphCache(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name ="id") var id: Int,
    @ColumnInfo(name ="order") var order: Int,
    @ColumnInfo(name ="paragraph") var paragraph: String,
    @ColumnInfo(name ="article_id") var articleId: Int)