package tour.donnees.journal.data.remote.response

import com.google.gson.annotations.SerializedName

open class FrontPageResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("created_at") var createdAt: String)

class NewsFromFrontPageResponse(
    @SerializedName("front_page_id") var frontPageId: Int,
    @SerializedName("article_id") var articleId: Int,
    @SerializedName("place") var place: String,
    @SerializedName("news") var news: NewsResponse?)

class FrontPageWithNewsResponse( id: Int, createdAt: String,
    @SerializedName("news") var news: List<NewsFromFrontPageResponse>): FrontPageResponse(id, createdAt)


