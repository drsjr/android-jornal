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
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import tour.donnees.journal.base.JournalResponseMock
import tour.donnees.journal.data.local.cache.*
import tour.donnees.journal.data.local.dao.FrontPageDao
import tour.donnees.journal.data.local.dao.FrontPageWithNewsDao
import tour.donnees.journal.data.local.dao.NewsDao
import tour.donnees.journal.data.local.dao.ParagraphDao
import tour.donnees.journal.data.repository.Repositories
import tour.donnees.journal.domain.mapper.FrontPageNewsMapper
import tour.donnees.journal.domain.mapper.NewsMapper
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.modal.FrontPageNews
import tour.donnees.journal.domain.repository.CategoryRepository
import tour.donnees.journal.domain.repository.FrontPageRepository
import tour.donnees.journal.domain.repository.NewsRepository
import tour.donnees.journal.domain.usecase.GetCategoryUseCase
import tour.donnees.journal.domain.usecase.GetFrontPageUseCase
import tour.donnees.journal.domain.usecase.GetFrontPageUseCaseImpl

class GetFrontPageUseCaseTest {

    @MockK
    private lateinit var frontPageRepository: FrontPageRepository

    @MockK
    private lateinit var newsRepository: NewsRepository

    private val getFrontPageUseCase: GetFrontPageUseCase by lazy {
        UseCases.getFrontPageUseCase(
            frontPageRepository,
            newsRepository
        )
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        frontPageRepository = mockk()
        newsRepository = mockk()

        every { frontPageRepository.getFrontPageWithNews(any()) } returns getFrontPageWithNews()
        every { newsRepository.getLocalNewsAndCategoryByArticleId(any()) } returns getLocalNewsAndCategoryByArticleId()
    }

    @Test
    fun getFrontPageUseCaseSuccessTest() {
        val testObserver = TestObserver<Result<List<FrontPageNews>>>()

        val result = getFrontPageUseCase.invoke("test")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val frontPage = testObserver.values()[0]

        Assert.assertTrue(frontPage.isSuccess)

        val test = getLocalNewsAndCategoryByArticleId()

        frontPage.fold(
            {
                val first = it[0]
                Assert.assertEquals("main", first.place)
                Assert.assertEquals(test.category.id, first.news.category.id)
                Assert.assertEquals(test.category.name, first.news.category.name)
                Assert.assertEquals(test.category.path, first.news.category.path)

                Assert.assertEquals(test.news.id, first.news.id)
                Assert.assertEquals(test.news.imageUrl, first.news.imageUrl)
                Assert.assertEquals(test.news.title, first.news.title)
                Assert.assertEquals(test.news.subtitle, first.news.subtitle)

            },
            {

            }
        )
    }

    private fun getFrontPageWithNews(): Observable<Result<List<FrontPageWithNewsCache>>> {
        return Observable.just(
            Result.success(
                listOf(
                    FrontPageWithNewsCache(
                        1,
                        1,
                        "main"
                    )
                )
            )
        )
    }

    private fun getLocalNewsAndCategoryByArticleId(): NewsAndCategoryCache {
        return NewsAndCategoryCache(
            NewsCache(1, "test", "test", "test", "test", "test", 1),
            CategoryCache(1, "test", "test", 1, false)
        )
    }
}