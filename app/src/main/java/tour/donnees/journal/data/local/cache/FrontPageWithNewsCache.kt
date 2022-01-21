package tour.donnees.journal.data.local.cache

import androidx.room.*

@Entity(tableName = "front_page_news", primaryKeys = ["front_page_id", "article_id"])
class FrontPageWithNewsCache(
    @ColumnInfo(name = "front_page_id") var frontPageId: Int,
    @ColumnInfo(name = "article_id") var articleId: Int,
    @ColumnInfo(name = "place") var place: String)


data class NewsFromFrontPageCache(
    @Embedded val frontPageItem: FrontPageWithNewsCache,
    @Relation(
        parentColumn = "article_id",
        entityColumn = "id"
    )
    val news: NewsCache
)