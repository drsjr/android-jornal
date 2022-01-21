package tour.donnees.journal.presentation.splash

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    sharedPreference: JournalSharedPreference
): BaseViewModel(sharedPreference) {

    val isLoggedIn = MutableLiveData<Boolean>()

    fun verifyLoggedIn() {
        when(sharedPreference.getToken()) {
            EMPTY -> isLoggedIn.postValue(false)
            else -> isLoggedIn.postValue(true)
        }
    }

    companion object {
        const val EMPTY = ""
    }
}