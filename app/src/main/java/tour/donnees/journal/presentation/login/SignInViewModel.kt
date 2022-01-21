package tour.donnees.journal.presentation.login

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.functions.Consumer
import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.domain.modal.UserInfo
import tour.donnees.journal.domain.usecase.GetUserInfoUseCase
import tour.donnees.journal.domain.usecase.SetSignInUseCase
import tour.donnees.journal.presentation.base.BaseViewModel
import tour.donnees.journal.presentation.base.error
import tour.donnees.journal.presentation.base.running
import tour.donnees.journal.presentation.base.success
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    sharedPreference: JournalSharedPreference,
    private val setSignInUseCase: SetSignInUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
    ): BaseViewModel(sharedPreference) {

    lateinit var userInfo: UserInfo
    var token = MutableLiveData<String>()


    fun signIn(username: String, password: String) {
        networkState.running()
        setSignInUseCase.invoke(username, password)
            .subscribe(getSignInConsumer())
    }

    fun getUserInfo() {
        networkState.running()
        getUserInfoUseCase.invoke(getToken())
            .subscribe(getUserInfoConsumer())
    }

    private fun getUserInfoConsumer(): Consumer<Result<UserInfo>> {
        return Consumer { result ->
            result.fold({ response ->
                userInfo = response
                networkState.success(USER_ME_RESPONSE_SUCCESS)
            }, {
                networkState.error(RESPONSE_ERROR)
            })
        }
    }

    private fun getSignInConsumer(): Consumer<Result<String>> {
        return Consumer { result ->
            result.fold({
                networkState.success(SIGN_IN_RESPONSE_SUCCESS)
            }, {
                networkState.error(RESPONSE_ERROR)
                throwError(it)
            })
        }
    }

    companion object {
        const val SIGN_IN_RESPONSE_SUCCESS = "SIGN_IN_RESPONSE_SUCCESS"
        const val USER_ME_RESPONSE_SUCCESS = "USER_ME_RESPONSE_SUCCESS"
        const val RESPONSE_ERROR = "RESPONSE_ERROR"
    }
}