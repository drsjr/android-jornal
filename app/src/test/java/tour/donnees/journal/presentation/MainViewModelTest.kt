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
import tour.donnees.journal.domain.modal.FrontPage
import tour.donnees.journal.domain.modal.FrontPageNews
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.domain.usecase.*
import tour.donnees.journal.presentation.base.NetworkState
import tour.donnees.journal.presentation.main.MainViewModel

class MainViewModelTest {

    @MockK
    private lateinit var sharedPreference: JournalSharedPreference

    @MockK
    private lateinit var getAllCategoriesUseCase: GetAllCategoriesUseCase

    @MockK
    private lateinit var getFrontPageUpdateUseCase: GetFrontPageUpdateUseCase

    @MockK
    private lateinit var getFrontPageUseCase: GetFrontPageUseCase

    @get:Rule
    val testRule = InstantTaskExecutorRule()


    private val viewModel: MainViewModel by lazy {
        ViewModel.getMainViewModel(
            getAllCategoriesUseCase,
            getFrontPageUpdateUseCase,
            getFrontPageUseCase,
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

        getAllCategoriesUseCase = mockk()
        every { getAllCategoriesUseCase.invoke(any()) } returns getGetAllCategoriesUseCaseInvoke()

        getFrontPageUpdateUseCase = mockk()
        every { getFrontPageUpdateUseCase.invoke(any()) } returns getGetFrontPageUpdateUseCaseInvoke()

        getFrontPageUseCase = mockk()
        every { getFrontPageUseCase.invoke(any()) } returns getGetFrontPageUseCaseInvoke()

    }


    @Test
    fun getFrontPageUpdateSuccessTest() {
        viewModel.getFrontPageUpdate()
        Assert.assertEquals(NetworkState.NetworkStateStatus.SUCCESS, viewModel.networkState.value!!.status)
        Assert.assertEquals(1, viewModel.frontPage.value!!.id)
        Assert.assertEquals("test", viewModel.frontPage.value!!.createdAt)

    }

    @Test
    fun getAllCategoriesSuccessTest() {
        viewModel.getAllCategories()
        Assert.assertEquals(NetworkState.NetworkStateStatus.SUCCESS, viewModel.networkState.value!!.status)
        Assert.assertEquals(1, viewModel.mapCategories.size)

    }

    @Test
    fun getFrontPageNewsSuccessTest() {
        viewModel.getFrontPageNews()
        Assert.assertEquals(NetworkState.NetworkStateStatus.SUCCESS, viewModel.networkState.value!!.status)
        Assert.assertEquals(1, viewModel.frontPageNews.value!!.size)
    }


    private fun getGetAllCategoriesUseCaseInvoke(): Observable<Result<List<Category>>> {
        return Observable.just(
            Result.success(
                listOf(
                    Category(1, "test", "test")
                )
            )
        )
    }

    private fun getGetFrontPageUpdateUseCaseInvoke(): Observable<Result<FrontPage>> {
        return Observable.just(
            Result.success(
                FrontPage(1, "test")
            )
        )
    }

    private fun getGetFrontPageUseCaseInvoke(): Observable<Result<List<FrontPageNews>>> {
        return Observable.just(
            Result.success(
                listOf(
                    FrontPageNews(
                        place = "main",
                        news = News(1, "test", "test", "test", "test", "test",
                            Category(1, "test", "test")
                        )
                    )
                )
            )
        )
    }

}