package tour.donnees.journal.data.remote.response

import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(@SerializedName("detail") val detail: ApiError)

data class ApiError(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("short") val short: String
)
