package tour.donnees.journal.presentation

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tour.donnees.journal.base.JournalResponseMock
import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.data.local.cache.FrontPageCache
import tour.donnees.journal.domain.modal.UserInfo
import tour.donnees.journal.domain.usecase.GetUserInfoUseCase
import tour.donnees.journal.domain.usecase.SetSignInUseCase
import tour.donnees.journal.presentation.base.NetworkState
import tour.donnees.journal.presentation.login.SignInViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule


class SignInViewModelTest {

    @MockK
    private lateinit var sharedPreference: JournalSharedPreference

    @MockK
    private lateinit var setSignInUseCase: SetSignInUseCase

    @MockK
    private lateinit var getUserInfoUseCase: GetUserInfoUseCase

    @get:Rule
    val testRule = InstantTaskExecutorRule()


    private val viewModel: SignInViewModel by lazy {
        ViewModel.getSignInViewModel(
            sharedPreference,
            setSignInUseCase,
            getUserInfoUseCase)
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        sharedPreference = mockk()
        every { sharedPreference.getToken() } returns "token"

        setSignInUseCase = mockk()
        every { setSignInUseCase.invoke(any(), any()) } returns getSetSignInUseCaseInvoke()

        getUserInfoUseCase = mockk()
        every { getUserInfoUseCase.invoke(any()) } returns getGetUserInfoUseCaseInvoke()

    }

    @Test
    fun signInSuccessTest() {
        viewModel.signIn("test@test.com", "test")
        Assert.assertEquals(NetworkState.NetworkStateStatus.SUCCESS, viewModel.networkState.value!!.status)
        Assert.assertEquals(SignInViewModel.SIGN_IN_RESPONSE_SUCCESS, viewModel.networkState.value!!.detail)
    }

    @Test
    fun getUserInfoSuccessTest() {
        viewModel.getUserInfo()
        Assert.assertEquals(NetworkState.NetworkStateStatus.SUCCESS, viewModel.networkState.value!!.status)
        Assert.assertEquals(SignInViewModel.USER_ME_RESPONSE_SUCCESS, viewModel.networkState.value!!.detail)

        Assert.assertEquals(1, viewModel.userInfo.id)
        Assert.assertEquals("test", viewModel.userInfo.fullName)
        Assert.assertEquals("test@test.com", viewModel.userInfo.email)
        Assert.assertEquals("test", viewModel.userInfo.updatedAt)
    }

    private fun getSetSignInUseCaseInvoke(): Observable<Result<String>> {
        return Observable.just(
            Result.success(
                "token"
            )
        )
    }

    private fun getGetUserInfoUseCaseInvoke(): Observable<Result<UserInfo>> {
        return Observable.just(
            Result.success(
                UserInfo(1, "test", "test@test.com", "test")
            )
        )
    }
}