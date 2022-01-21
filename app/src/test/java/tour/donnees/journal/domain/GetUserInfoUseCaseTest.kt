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
import tour.donnees.journal.data.local.cache.UserInfoCache
import tour.donnees.journal.domain.modal.UserInfo
import tour.donnees.journal.domain.repository.SignInRepository
import tour.donnees.journal.domain.usecase.GetUserInfoUseCase

class GetUserInfoUseCaseTest {

    @MockK
    private lateinit var repository: SignInRepository

    private val getUserInfoUseCase: GetUserInfoUseCase by lazy {
        UseCases.getUserInfoUseCase(repository)
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        repository = mockk()
        every { repository.getUserInfo(any()) } returns getUserInfo()
    }

    @Test
    fun getUserInfoUseCaseSuccessTest() {
        val testObserver = TestObserver<Result<UserInfo>>()

        val result = getUserInfoUseCase.invoke("test")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val categories = testObserver.values()[0]

        val test = UserInfoCache(1, "test@test.com", "Test test", "test")

        Assert.assertTrue(categories.isSuccess)

        categories.fold({
            Assert.assertEquals(test.id, it.id)
            Assert.assertEquals(test.email, it.email)
            Assert.assertEquals(test.fullName, it.fullName)
            Assert.assertEquals(test.updatedAt, it.updatedAt)
        }, {

        })
    }

    private fun getUserInfo(): Observable<Result<UserInfoCache>> {
        return Observable.just(
            Result.success(
                UserInfoCache(1, "test@test.com", "Test test", "test")
            )
        )
    }

}