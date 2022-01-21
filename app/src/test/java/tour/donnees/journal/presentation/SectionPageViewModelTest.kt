package tour.donnees.journal.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tour.donnees.journal.core.shared.JournalSharedPreference
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.domain.usecase.GetNewsByCategoryUseCase
import tour.donnees.journal.presentation.base.NetworkState
import tour.donnees.journal.presentation.section.SectionPageViewModel

class SectionPageViewModelTest {

    @MockK
    private lateinit var sharedPreference: JournalSharedPreference

    @MockK
    private lateinit var getNewsByCategoryUseCase: GetNewsByCategoryUseCase

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val viewModel: SectionPageViewModel by lazy {
        ViewModel.getSectionPageViewModel(
            getNewsByCategoryUseCase,
            sharedPreference)
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        sharedPreference = mockk()
        every { sharedPreference.getToken() } returns "token"

        getNewsByCategoryUseCase = mockk()
        every { getNewsByCategoryUseCase.invoke(any(), any(), any(), any()) } returns getNewsByCategoryUseCaseInvoke()

    }

    @Test
    fun test() {
        viewModel.getNewsByCategory(1, 0, 10)
        Assert.assertEquals(NetworkState.NetworkStateStatus.SUCCESS, viewModel.networkState.value!!.status)
        Assert.assertEquals(1, viewModel.news.value!!.size)

    }

    private fun getNewsByCategoryUseCaseInvoke(): Observable<Result<List<News>>> {
        return Observable.just(
            Result.success(
                listOf(
                    News(1, "test", "test", "test", "test", "test",
                        Category(1, "test", "test")
                    )
                )
            )
        )
    }

}