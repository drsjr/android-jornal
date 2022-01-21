package tour.donnees.journal.data.remote.response

import com.google.gson.annotations.SerializedName


data class NewsResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("url") var url: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("title") var title: String,
    @SerializedName("subtitle") var subtitle: String,
    @SerializedName("image") var imageUrl: String,
    @SerializedName("category") var categoryId: Int)