package tour.donnees.journal.domain

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tour.donnees.journal.base.JournalResponseMock
import tour.donnees.journal.data.local.cache.CategoryCache
import tour.donnees.journal.data.local.dao.CategoryDao
import tour.donnees.journal.data.local.dao.FrontPageDao
import tour.donnees.journal.data.local.dao.FrontPageWithNewsDao
import tour.donnees.journal.data.local.dao.NewsDao
import tour.donnees.journal.data.repository.Repositories
import tour.donnees.journal.domain.mapper.CategoryMapper
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.repository.CategoryRepository
import tour.donnees.journal.domain.usecase.GetAllCategoriesUseCase
import tour.donnees.journal.domain.usecase.GetCategoryUseCase
import tour.donnees.journal.domain.usecase.GetCategoryUseCaseImpl

class GetCategoryUseCaseTest {

    @MockK
    private lateinit var repository: CategoryRepository

    private val getCategoryUseCase: GetCategoryUseCase by lazy {
        UseCases.getCategoryUseCase(repository)
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        repository = mockk()
        every { repository.getCategoriesById(any(), any()) } returns getCategoryByIdSuccess()
    }

    @Test
    fun getCategoryUseCaseSuccessTest() {
        val testObserver = TestObserver<Result<Category>>()

        val result = getCategoryUseCase.invoke("test", 1)

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val category = testObserver.values()[0]

        Assert.assertTrue(category.isSuccess)

        category.fold({
            Assert.assertEquals(1, it.id)
            Assert.assertEquals("test", it.name)
            Assert.assertEquals("test", it.path)
        }, {

        })

    }

    private fun getCategoryByIdSuccess(): Observable<Result<CategoryCache>> {
        return Observable.just(
            Result.success(
                CategoryCache(1,"test", "test", 1, false)
            )
        )
    }
}