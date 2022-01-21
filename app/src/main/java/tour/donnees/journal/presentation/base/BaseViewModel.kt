package tour.donnees.journal.presentation.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tour.donnees.journal.core.BaseException
import tour.donnees.journal.core.NoConnectionException
import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.data.remote.response.ApiError

open class BaseViewModel(protected val sharedPreference: JournalSharedPreference): ViewModel() {

    var error = MutableLiveData<ApiError>()
    var networkState = MutableLiveData<NetworkState>()

    fun getToken(): String {
        return "Bearer ${sharedPreference.getToken()}"
    }

    fun removeToken() = sharedPreference.removeToken()

    fun throwError(exception: Throwable) {
        when (exception) {
            is BaseException -> error.postValue(exception.error)
            is NoConnectionException -> Log.v("NO_CONNECTION_EXCEPTION", "visss")
            else -> exception.printStackTrace()
        }
    }
}