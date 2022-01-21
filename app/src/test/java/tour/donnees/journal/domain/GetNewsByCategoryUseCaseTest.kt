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
import tour.donnees.journal.data.local.cache.NewsAndCategoryCache
import tour.donnees.journal.data.local.cache.NewsCache
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.domain.repository.NewsRepository
import tour.donnees.journal.domain.usecase.GetNewsByCategoryUseCase

class GetNewsByCategoryUseCaseTest {

    @MockK
    private lateinit var newsRepository: NewsRepository

    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase by lazy {
        UseCases.getNewsByCategoryUseCase(
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

        every { newsRepository.getNewsByCategory(any(), any(), any(), any()) } returns getNewsByCategory()
    }

    @Test
    fun getNewsByCategoryUseCaseSuccessTest() {
        val testObserver = TestObserver<Result<List<News>>>()

        val result = getNewsByCategoryUseCase.invoke("test", 1, 0, 10)

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val newsByCategory = testObserver.values()[0]

        Assert.assertTrue(newsByCategory.isSuccess)

        val test = NewsAndCategoryCache(
            NewsCache(1, "test", "test", "test", "test", "test", 1),
            CategoryCache(1, "test", "test", 1, false)
        )

        newsByCategory.fold(
            {
                val first = it[0]
                Assert.assertEquals(test.category.id, first.category.id)
                Assert.assertEquals(test.category.name, first.category.name)
                Assert.assertEquals(test.category.path, first.category.path)

                Assert.assertEquals(test.news.id, first.id)
                Assert.assertEquals(test.news.imageUrl, first.imageUrl)
                Assert.assertEquals(test.news.title, first.title)
                Assert.assertEquals(test.news.subtitle, first.subtitle)
            },
            {
            }
        )
    }

    private fun getNewsByCategory(): Observable<Result<List<NewsAndCategoryCache>>> {
        return Observable.just(
            Result.success(
                listOf(
                    NewsAndCategoryCache(
                        NewsCache(1, "test", "test", "test", "test", "test", 1),
                        CategoryCache(1, "test", "test", 1, false)
                    )
                )
            )
        )
    }


}