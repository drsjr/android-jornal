package tour.donnees.journal.presentation.base

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso


fun Picasso.setImageView(url: String, view: ImageView) {
    this
        .load(url)
        .into(view)
}

fun MutableLiveData<NetworkState>.running(detail: String? = null) {
    this.postValue(NetworkState(NetworkState.NetworkStateStatus.RUNNING, detail))
}

fun MutableLiveData<NetworkState>.success(detail: String? = null) {
    this.postValue(NetworkState(NetworkState.NetworkStateStatus.SUCCESS, detail))
}

fun MutableLiveData<NetworkState>.error(detail: String? = null) {
    this.postValue(NetworkState(NetworkState.NetworkStateStatus.ERROR, detail))
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
fun View.invisible() {
    this.visibility = View.INVISIBLE
}
