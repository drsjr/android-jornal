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
import tour.donnees.journal.data.local.cache.FrontPageCache
import tour.donnees.journal.domain.modal.FrontPage
import tour.donnees.journal.domain.repository.FrontPageRepository
import tour.donnees.journal.domain.usecase.GetFrontPageUpdateUseCase

class GetFrontPageUpdateUseCaseTest {

    @MockK
    private lateinit var frontPageRepository: FrontPageRepository

    private val getFrontPageUpdateUseCase: GetFrontPageUpdateUseCase by lazy {
        UseCases.getFrontPageUpdateUseCase(
            frontPageRepository
        )
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        frontPageRepository = mockk()
        every { frontPageRepository.getFrontPageUpdate(any()) } returns getFrontPageUpdate()
    }

    @Test
    fun getFrontPageUpdateUseCaseSuccessTest() {
        val testObserver = TestObserver<Result<FrontPage>>()

        val result = getFrontPageUpdateUseCase.invoke("test")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val categories = testObserver.values()[0]

        Assert.assertTrue(categories.isSuccess)

        categories.fold({
            Assert.assertEquals(1, it.id)
            Assert.assertEquals("test", it.createdAt)
        }, {

        })
    }

    private fun getFrontPageUpdate(): Observable<Result<FrontPageCache>> {
        return Observable.just(
            Result.success(
                FrontPageCache(1, "test")
            )
        )
    }

}