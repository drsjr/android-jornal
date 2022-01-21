package tour.donnees.journal.data.remote.response

import com.google.gson.annotations.SerializedName


data class CategoryResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("path") var path: String,
    @SerializedName("code") var code: Int,
    @SerializedName("disabled") var disabled: Boolean)