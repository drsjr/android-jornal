package tour.donnees.journal.domain.modal


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var id: Int,
    var name: String,
    var path: String
): Parcelable