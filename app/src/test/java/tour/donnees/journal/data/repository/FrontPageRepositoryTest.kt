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
import tour.donnees.journal.base.JournalResponseMock.Companion.RESPONSE_FRONTPAGE_200
import tour.donnees.journal.base.JournalResponseMock.Companion.RESPONSE_FRONTPAGE_UPDATE_200
import tour.donnees.journal.data.local.cache.FrontPageCache
import tour.donnees.journal.data.local.cache.FrontPageWithNewsCache
import tour.donnees.journal.data.local.cache.NewsCache
import tour.donnees.journal.data.local.cache.NewsFromFrontPageCache
import tour.donnees.journal.data.local.dao.FrontPageDao
import tour.donnees.journal.data.local.dao.FrontPageWithNewsDao
import tour.donnees.journal.data.local.dao.NewsDao

class FrontPageRepositoryTest {

    private val service = JournalResponseMock()

    @MockK
    private lateinit var frontPageDao: FrontPageDao

    @MockK
    private lateinit var newsDao: NewsDao

    @MockK
    private lateinit var frontPageWithNewsDao: FrontPageWithNewsDao

    private val repository: FrontPageRepositoryImpl by lazy {
        Repositories.getFrontPageRepository(
            service.getJournalService(),
            frontPageDao,
            newsDao,
            frontPageWithNewsDao)
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }


    @Before
    fun setup() {
        frontPageDao = mockk()
        every { frontPageDao.insert(any()) } returns Unit

        frontPageWithNewsDao = mockk()
        every { frontPageWithNewsDao.insert(any()) } returns Unit
        every { frontPageWithNewsDao.getFrontPageNews(any()) } returns getFrontPageWithNewsList()
        every { frontPageWithNewsDao.getNewsFromFrontPage(any()) } returns getNewsFromFrontPageCacheList()

        newsDao = mockk()
        every { newsDao.insert(any()) } returns Unit

    }

    @Test
    fun requestFrontPageUpdateSuccess() {
        service.setEndpointContent(RESPONSE_FRONTPAGE_UPDATE_200, 200)

        val testObserver = TestObserver<Result<FrontPageCache>>()

        val result = repository.getFrontPageUpdate("test")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val frontPage = testObserver.values()[0]

        Assert.assertEquals(true, frontPage.isSuccess)

        frontPage.onSuccess {
            Assert.assertEquals(101, it.id)
            Assert.assertEquals("2021-10-02 00:13:02.833783", it.createdAt)
        }

    }

    @Test
    fun requestFrontPageSuccess() {
        service.setEndpointContent(RESPONSE_FRONTPAGE_200, 200)

        val testObserver = TestObserver<Result<List<FrontPageWithNewsCache>>>()

        val result = repository.getFrontPageWithNews("test")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val frontPage = testObserver.values()[0]

        Assert.assertEquals(true, frontPage.isSuccess)

    }

    private fun getFrontPageWithNewsList(): List<FrontPageWithNewsCache> {
        return listOf(
            FrontPageWithNewsCache(
                frontPageId = 101,
                articleId = 28,
                place = "Cultura"
            )
        )
    }

    private fun getNewsFromFrontPageCacheList(): List<NewsFromFrontPageCache> {
        return listOf(
            NewsFromFrontPageCache(
                FrontPageWithNewsCache(1, 1, "main"),
                NewsCache(1, "test", "test", "test", "test", "test", 1)
            )
        )
    }
}