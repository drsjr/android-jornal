package tour.donnees.journal.data.remote.request

import com.google.gson.annotations.SerializedName

class TokenRequest(
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String,
    @SerializedName("grand_type") var grandType: String = "",
    @SerializedName("scope") var scope: String = ""
)