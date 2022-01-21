package tour.donnees.journal.data.repository

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.data.base.BaseRepository
import tour.donnees.journal.data.local.cache.NewsAndCategoryCache
import tour.donnees.journal.data.local.cache.NewsCache
import tour.donnees.journal.data.local.cache.ParagraphCache
import tour.donnees.journal.data.local.dao.NewsDao
import tour.donnees.journal.data.local.dao.ParagraphDao
import tour.donnees.journal.data.mapper.NewsCacheMapper
import tour.donnees.journal.data.mapper.ParagraphCacheMapper
import tour.donnees.journal.data.remote.api.JournalService
import tour.donnees.journal.data.remote.response.NewsResponse
import tour.donnees.journal.data.remote.response.ParagraphResponse
import tour.donnees.journal.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject
    constructor(
    private val service: JournalService,
    private val newsCacheMapper: NewsCacheMapper,
    private val paragraphCacheMapper: ParagraphCacheMapper,
    private val newsDao: NewsDao,
    private val paragraphDao: ParagraphDao): BaseRepository(), NewsRepository {

    override fun getArticleParagraph(
        token: String,
        articleId: Int
    ): Observable<Result<List<ParagraphCache>>> {
        return service.getArticleParagraphs(token, articleId).map { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { body ->
                        insertParagraphs(body)
                        Result.success(getParagraphsByArticleId(articleId))
                    } ?: Result.failure(Exception())
                }
                else -> Result.failure(handling(response.errorBody()))
            }
        }.onErrorReturn { Result.failure(it) }
    }

    override fun getNewsByArticleId(token: String, articleId: Int): Observable<Result<NewsCache>> {
        return service.getNewsByArticleId(token, articleId).map { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { news ->
                        insertNews(news)
                        Result.success(getNewsByArticleId(articleId))
                    } ?: Result.failure(Exception())
                }
                else -> Result.failure(handling(response.errorBody()))
            }
        }.onErrorReturn { Result.failure(it) }
    }

    override fun getNewsByCategory(token: String, categoryId: Int, offset: Int, limit: Int):
            Observable<Result<List<NewsAndCategoryCache>>> {
        return service.getNewsByCategory(token, categoryId, offset, limit).map { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { body ->
                        body.map { newsDao.insert(newsCacheMapper.mapFrom(it)) }
                        Result.success(getNewsByArticleListId(body.map { it.id }))
                    } ?: Result.failure(Exception())
                }
                else -> Result.failure(handling(response.errorBody()))
            }
        }.onErrorReturn { Result.failure(it) }
    }

    override fun getLocalNewsAndCategoryByArticleId(articleId: Int): NewsAndCategoryCache {
        return getNewsAndCategoryByArticleId(articleId)
    }

    private fun insertParagraphs(paragraphResponse: List<ParagraphResponse>) {
        paragraphResponse.forEach {
            paragraphDao.insert(paragraphCacheMapper.mapFrom(it))
        }
    }

    private fun insertNews(news: NewsResponse?) {
        news?.let {
            newsDao.insert(newsCacheMapper.mapFrom(it))
        }
    }

    private fun getParagraphsByArticleId(articleId: Int): List<ParagraphCache> {
        return paragraphDao.getParagraphByArticleId(articleId)
    }

    private fun getNewsByArticleId(articleId: Int): NewsCache {
        return newsDao.getNewsByArticleId(articleId)
    }

    private fun getNewsByArticleListId(articleIds: List<Int>): List<NewsAndCategoryCache> {
        return articleIds.map {
            newsDao.getNewsAndCategoryByArticleId(it)
        }.toList()
    }

    private fun getNewsAndCategoryByArticleId(articleId: Int): NewsAndCategoryCache {
        return newsDao.getNewsAndCategoryByNewsId(articleId)
    }
}