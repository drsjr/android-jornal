package tour.donnees.journal.data.remote.response

import com.google.gson.annotations.SerializedName


data class TokenResponse(
    @SerializedName("token_type") var type: String,
    @SerializedName("access_token") var token: String)