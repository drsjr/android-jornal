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
import tour.donnees.journal.domain.modal.Paragraph
import tour.donnees.journal.domain.usecase.GetParagraphUseCase
import tour.donnees.journal.presentation.article.ArticleViewModel
import tour.donnees.journal.presentation.base.NetworkState

class ArticleViewModelTest {

    @MockK
    private lateinit var sharedPreference: JournalSharedPreference

    @MockK
    private lateinit var getParagraphUseCase: GetParagraphUseCase

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val viewModel: ArticleViewModel by lazy {
        ViewModel.getArticleViewModel(
            getParagraphUseCase,
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

        getParagraphUseCase = mockk()
        every { getParagraphUseCase.invoke(any(), any()) } returns getGetParagraphUseCaseInvoke()

    }

    @Test
    fun getParagraphsSuccessTest() {
        viewModel.getParagraphs(1)
        Assert.assertEquals(NetworkState.NetworkStateStatus.SUCCESS, viewModel.networkState.value!!.status)
        Assert.assertEquals(1, viewModel.paragraphs.value!!.size)
    }


    fun getGetParagraphUseCaseInvoke(): Observable<Result<List<Paragraph>>> {
        return Observable.just(
            Result.success(
                listOf(
                    Paragraph(1, "test", 1)
                )
            )
        )
    }


}