package tour.donnees.journal.domain.modal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    var id: Int,
    var fullName: String,
    var email: String,
    var updatedAt: String
) : Parcelable