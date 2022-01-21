package tour.donnees.journal.data.local.cache

import androidx.room.Relation
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news"/*,
    foreignKeys =
        [ForeignKey(
            entity = CategoryCache::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("category_id"),
            onDelete = ForeignKey.CASCADE)]
*/)
data class NewsCache(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name ="id") var id: Int,
    @ColumnInfo(name ="url") var url: String,
    @ColumnInfo(name ="created_at") var createdAt: String,
    @ColumnInfo(name ="title") var title: String,
    @ColumnInfo(name ="subtitle") var subtitle: String,
    @ColumnInfo(name ="image") var imageUrl: String,
    @ColumnInfo(name ="category_id") var categoryId: Int)


data class NewsAndCategoryCache(
    @Embedded val news: NewsCache,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: CategoryCache
)