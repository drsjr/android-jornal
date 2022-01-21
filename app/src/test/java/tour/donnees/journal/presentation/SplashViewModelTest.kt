package tour.donnees.journal.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.presentation.splash.SplashViewModel

class SplashViewModelTest {

    @MockK
    private lateinit var sharedPreference: JournalSharedPreference

    @get:Rule
    val testRule = InstantTaskExecutorRule()


    private val viewModel: SplashViewModel by lazy {
        ViewModel.getSplashViewModel(
            sharedPreference
        )
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        sharedPreference = mockk()
        every { sharedPreference.getToken() } returns "token"

    }

    @Test
    fun verifyLoggedInTrueTest() {
        viewModel.verifyLoggedIn()
        Assert.assertTrue(viewModel.isLoggedIn.value as Boolean)
    }

    @Test
    fun verifyLoggedInFalseTest() {
        every { sharedPreference.getToken() } returns ""

        viewModel.verifyLoggedIn()
        Assert.assertFalse(viewModel.isLoggedIn.value as Boolean)
    }

}