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
import tour.donnees.journal.data.local.cache.NewsAndCategoryCache
import tour.donnees.journal.data.local.cache.NewsCache
import tour.donnees.journal.data.local.cache.ParagraphCache
import tour.donnees.journal.data.local.dao.NewsDao
import tour.donnees.journal.data.local.dao.ParagraphDao
import tour.donnees.journal.data.repository.Repositories
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.domain.modal.Paragraph
import tour.donnees.journal.domain.repository.NewsRepository
import tour.donnees.journal.domain.usecase.GetNewsByCategoryUseCase
import tour.donnees.journal.domain.usecase.GetParagraphUseCase
import tour.donnees.journal.domain.usecase.GetParagraphUseCaseImpl

class GetParagraphUseCaseTest {

    @MockK
    private lateinit var newsRepository: NewsRepository

    private val getParagraphUseCase: GetParagraphUseCase by lazy {
        UseCases.getParagraphUseCase(
            newsRepository
        )
    }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        newsRepository = mockk()

        every { newsRepository.getArticleParagraph(any(), any()) } returns getArticleParagraph()
    }

    @Test
    fun getParagraphUseCaseTestSuccessTest() {
        val testObserver = TestObserver<Result<List<Paragraph>>>()

        val result = getParagraphUseCase.invoke("test", 1)

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val newsByCategory = testObserver.values()[0]

        val test = ParagraphCache(1, 1, "test", 1)

        Assert.assertTrue(newsByCategory.isSuccess)

        newsByCategory.fold(
            {
                val first = it[0]
                Assert.assertEquals(test.id, first.id)
                Assert.assertEquals(test.order, first.order)
                Assert.assertEquals(test.paragraph, first.paragraph)
            },
            {
            }
        )
    }

    private fun getArticleParagraph(): Observable<Result<List<ParagraphCache>>> {
        return Observable.just(
            Result.success(
                listOf(
                    ParagraphCache(1, 1, "test", 1)
                )
            )
        )
    }
}