package tour.donnees.journal.data.repository

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tour.donnees.journal.base.JournalResponseMock
import tour.donnees.journal.base.JournalResponseMock.Companion.RESPONSE_CATEGORY_200
import tour.donnees.journal.base.JournalResponseMock.Companion.RESPONSE_CATEGORY_ID_200
import tour.donnees.journal.data.local.cache.CategoryCache
import tour.donnees.journal.data.local.dao.CategoryDao
import tour.donnees.journal.domain.modal.Category

class CategoryRepositoryTest {

    private val service = JournalResponseMock()
    @MockK private lateinit var categoryDao: CategoryDao
    
    private val repository: CategoryRepositoryImpl by lazy { Repositories.getCategoryRepository(service.getJournalService(), categoryDao) }

    init {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler{ Schedulers.trampoline() }
    }

    @Before
    fun setup() {
        categoryDao = mockk()
        every { categoryDao.insert(any()) } returns Unit
        every { categoryDao.getCategoryById(any()) } returns CategoryCache(1, "test", "test", 1, true)
        every { categoryDao.getAllCategories(false) } returns listOf(CategoryCache(1, "test", "test", 1, true))
    }

    @Test
    fun requestAllCategoriesSuccess() {
        val testObserver = TestObserver<Result<List<CategoryCache>>>()

        service.setEndpointContent(RESPONSE_CATEGORY_200,200)

        val result = repository.getAllCategories("test")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val categories = testObserver.values()[0]

        Assert.assertEquals(true, categories.isSuccess)
        Assert.assertEquals(1, categories.getOrNull()!!.size)
    }

    @Test
    fun requestCategoryByIdSuccess() {
        val testObserver = TestObserver<Result<CategoryCache>>()

        service.setEndpointContent(RESPONSE_CATEGORY_ID_200,200)

        val result = repository.getCategoriesById("test", 1)

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val category = testObserver.values()[0]

        Assert.assertEquals(true, category.isSuccess)
        assertCategory(category.getOrNull()!!)
    }

    @Test
    fun requestAllCategoriesSuccessTestMethod() {
        val testObserver = TestObserver<Result<List<CategoryCache>>>()

        service.setEndpointContent(RESPONSE_CATEGORY_200,200)

        val result = repository.getAllCategories("token")

        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val categories = testObserver.values()[0]

        Assert.assertEquals(true, categories.isSuccess)
        Assert.assertEquals(1, categories.getOrNull()!!.size)

    }

    private fun assertCategory(category: CategoryCache) {
        Assert.assertEquals(1, category.id)
        Assert.assertEquals("test", category.name)
        Assert.assertEquals("test", category.path)
    }
}














































































