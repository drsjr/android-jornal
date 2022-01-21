package tour.donnees.journal.data.remote.response

import com.google.gson.annotations.SerializedName

class UserInfoResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("email") var email: String,
    @SerializedName("full_name") var fullName: String,
    @SerializedName("updated_at") var updatedAt: String)