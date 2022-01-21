package tour.donnees.journal.domain.modal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    var id: Int,
    var url: String,
    var title: String,
    var subtitle: String,
    var createdAt: String,
    var imageUrl: String,
    var category: Category) : Parcelable