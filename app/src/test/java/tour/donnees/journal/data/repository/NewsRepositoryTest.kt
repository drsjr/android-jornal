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
import tour.donnees.journal.base.JournalResponseMock.Companion.RESPONSE_ARTICLE_ID_PARAGRAPHS
import tour.donnees.journal.data.local.cache.CategoryCache
import tour.donnees.journal.data.local.cache.NewsAndCategoryCache
import tour.donnees.journal.data.local.cache.NewsCache
import tour.donnees.journal.data.local.cache.ParagraphCache
import tour.donnees.journal.data.local.dao.NewsDao
import tour.donnees.journal.data.local.dao.ParagraphDao

class NewsRepositoryTest {

    private val service = JournalResponseMock()

    @MockK
    private lateinit var newsDao: NewsDao

    @MockK
    private lateinit var paragraphDao: ParagraphDao

    private val repository: NewsRepositoryImpl by lazy {
        Repositories.getNewsRepository(
            service.getJournalService(),
            newsDao,
            paragraphDao
        )
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        newsDao = mockk()
        paragraphDao = mockk()
        every { newsDao.insert(any()) } returns Unit
        every { paragraphDao.insert(any()) } returns Unit
        every { newsDao.getNewsByArticleId(any()) } returns NewsCache(1, "test", "test", "test", "test", "test", 1)
        every { paragraphDao.getParagraphByArticleId(any()) } returns listOf(ParagraphCache(1, 1, "test", 28))
        every { newsDao.getNewsAndCategoryByArticleId(any()) } returns
            NewsAndCategoryCache(
                news = NewsCache(1, "test", "test", "test", "test", "test", 1),
                category = CategoryCache(1, "test", "test", 1, false)
            )
    }

    @Test
    fun requestNewsByArticleIdSuccess() {
        every { newsDao.getNewsAndCategoryByNewsId(any()) } returns getNewsAndCategoryCacheMocked()


        val testObserver = TestObserver<Result<NewsCache>>()

        service.setEndpointContent(JournalResponseMock.RESPONSE_NEWS_ID_200, 200)
        val result = repository.getNewsByArticleId("test", 28)

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val news = testObserver.values()[0]

        Assert.assertEquals(true, news.isSuccess)
    }

    @Test
    fun requestParagraphsByArticleIdPageSuccess() {

        val testObserver = TestObserver<Result<List<ParagraphCache>>>()

        service.setEndpointContent(RESPONSE_ARTICLE_ID_PARAGRAPHS, 200)
        val result = repository.getArticleParagraph("token", 28)

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val paragraphs = testObserver.values()[0]

        Assert.assertEquals(true, paragraphs.isSuccess)

        paragraphs.onSuccess {
            Assert.assertEquals(1, paragraphs.getOrNull()!!.size)
        }
    }

    @Test
    fun requestNewsByCategorySuccess() {
        val testObserver = TestObserver<Result<List<NewsAndCategoryCache>>>()

        service.setEndpointContent(JournalResponseMock.RESPONSE_NEWS_CATEGORY_ID_200, 200)
        val result = repository.getNewsByCategory("token", 1, 0, 10)

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val newsByCategory = testObserver.values()[0]

        Assert.assertEquals(true, newsByCategory.isSuccess)
        Assert.assertEquals(10, newsByCategory.getOrNull()!!.size)
    }

    private fun getNewsAndCategoryCacheMocked(): NewsAndCategoryCache {
        return NewsAndCategoryCache(
            NewsCache(1, "test", "test", "test", "test", "test", 1),
            CategoryCache(1, "test", "test", 1, false)
        )
    }

    private fun getListNewsAndCategoryCacheMocked(): List<NewsAndCategoryCache> {
        val test = mutableListOf<NewsAndCategoryCache>()
        for (i in 1..10) {
            test.add(getNewsAndCategoryCacheMocked())
        }
        return test

    }

}