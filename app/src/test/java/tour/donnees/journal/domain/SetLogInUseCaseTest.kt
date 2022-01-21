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
import tour.donnees.journal.domain.repository.SignInRepository
import tour.donnees.journal.domain.usecase.SetSignInUseCase

class SetLogInUseCaseTest {

    @MockK
    private lateinit var repository: SignInRepository

    private val setSignInUseCase: SetSignInUseCase by lazy {
        UseCases.setSignInUseCase(repository)
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        repository = mockk()
        every { repository.getLoggedUser(any(), any()) } returns getTokenAccess()
    }

    @Test
    fun setSignInUseCaseSuccessTest() {
        val testObserver = TestObserver<Result<String>>()

        val result = setSignInUseCase.invoke("test@test.com", "test")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val categories = testObserver.values()[0]

        Assert.assertTrue(categories.isSuccess)

        categories.fold({
            Assert.assertEquals("test", it)
        }, {

        })
    }

    private fun getTokenAccess(): Observable<Result<String>> {
        return Observable.just(
            Result.success("test")
        )
    }
}