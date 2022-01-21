package tour.donnees.journal.data.repository

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tour.donnees.journal.base.JournalResponseMock
import tour.donnees.journal.base.JournalResponseMock.Companion.RESPONSE_TOKEN_200
import tour.donnees.journal.base.JournalResponseMock.Companion.RESPONSE_TOKEN_401
import tour.donnees.journal.base.JournalResponseMock.Companion.RESPONSE_USERS_ME_200
import tour.donnees.journal.core.BaseException
import tour.donnees.journal.data.local.cache.UserInfoCache
import tour.donnees.journal.data.local.dao.UserInfoDao

class SignInRepositoryTest {

    private val service = JournalResponseMock()

    @MockK
    private lateinit var userInfoDao: UserInfoDao

    private val repository: SignInRepositoryImpl by lazy { Repositories.getSignInRepository(service.getJournalService(), userInfoDao)}

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        userInfoDao = mockk()
        every { userInfoDao.insert(any()) } returns Unit
        every { userInfoDao.getUserById(any()) } returns UserInfoCache(1, "test@test.com", "Chocobo Amarelo", "2021-08-24 03:24:39.856378")

    }

    @Test
    fun requestTokenSuccess() {
        val testObserver = TestObserver<Result<String>>()

        service.setEndpointContent(RESPONSE_TOKEN_200, 200)
        val result = repository.getLoggedUser("test@test.com", "test")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val token = testObserver.values()[0]

        Assert.assertEquals(true, token.isSuccess)
        Assert.assertEquals(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzEwNjI0OTYsInN1YiI6InRlc3RAdGVzdC5jb20ifQ._-VJJUOfYKmZPXTSCaFGa4nPGdbqWGn7rN5RjhlVF9E",
            token.getOrNull())
    }

    @Test
    fun requestUserInfoSuccess() {
        val testObserver = TestObserver<Result<UserInfoCache>>()

        service.setEndpointContent(RESPONSE_USERS_ME_200, 200)
        val result = repository.getUserInfo("token")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val user = testObserver.values()[0]

        Assert.assertEquals(true, user.isSuccess)

        user.onSuccess {
            Assert.assertEquals(1, it.id)
            Assert.assertEquals("test@test.com", it.email)
            Assert.assertEquals("Chocobo Amarelo", it.fullName)
            Assert.assertEquals("2021-08-24 03:24:39.856378", it.updatedAt)
        }
    }

    @Test
    fun requestTokenInvalidError() {
        val testObserver = TestObserver<Result<String>>()

        service.setEndpointContent(RESPONSE_TOKEN_401, 401)
        val result = repository.getLoggedUser("test@test.com", "test")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val token = testObserver.values()[0]

        token.onFailure {
            val error = (it as BaseException).error
            Assert.assertEquals(true, token.isFailure)
            Assert.assertEquals(401, error.code)
            Assert.assertEquals("email or password invalid", error.message)
            Assert.assertEquals("incorrect_credential", error.short)
        }
    }
}