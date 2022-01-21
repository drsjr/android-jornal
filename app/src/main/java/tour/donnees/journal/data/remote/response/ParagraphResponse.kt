package tour.donnees.journal.data.remote.response

import com.google.gson.annotations.SerializedName

class ParagraphResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("paragraph") var paragraph: String,
    @SerializedName("order") var order: Int,
    @SerializedName("article_id") var articleId: Int)