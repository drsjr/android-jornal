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
import tour.donnees.journal.data.local.cache.CategoryCache
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.repository.CategoryRepository
import tour.donnees.journal.domain.usecase.GetAllCategoriesUseCase

class GetAllCategoriesUseCaseTest {

    @MockK
    private lateinit var repository: CategoryRepository

    private val getAllCategoriesUseCase: GetAllCategoriesUseCase by lazy {
        UseCases.getAllCategoriesUseCase(repository)
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        repository = mockk()
        every { repository.getAllCategories(any()) } returns getAllCategoriesSuccess()
    }



    @Test
    fun getAllCategoriesUseCaseSuccessTest() {
        val testObserver = TestObserver<Result<List<Category>>>()

        val result = getAllCategoriesUseCase.invoke("test")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val categories = testObserver.values()[0]

        Assert.assertTrue(categories.isSuccess)

        categories.fold({
            Assert.assertEquals(1, it.size)
            Assert.assertEquals(1, it[0].id)
            Assert.assertEquals("test", it[0].name)
            Assert.assertEquals("test", it[0].path)
        }, {

        })

    }

    private fun getAllCategoriesSuccess(): Observable<Result<List<CategoryCache>>> {
        return Observable.just(
            Result.success(
                listOf(
                    CategoryCache(1,"test", "test", 1, false)
                )
            )
        )
    }
}