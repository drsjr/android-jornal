package tour.donnees.journal.data.repository

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.data.base.BaseRepository
import tour.donnees.journal.data.local.cache.FrontPageCache
import tour.donnees.journal.data.local.cache.FrontPageWithNewsCache
import tour.donnees.journal.data.local.cache.NewsFromFrontPageCache
import tour.donnees.journal.data.local.dao.FrontPageDao
import tour.donnees.journal.data.local.dao.FrontPageWithNewsDao
import tour.donnees.journal.data.local.dao.NewsDao
import tour.donnees.journal.data.mapper.FrontPageCacheMapper
import tour.donnees.journal.data.mapper.FrontPageWithNewsCacheMapper
import tour.donnees.journal.data.mapper.NewsCacheMapper
import tour.donnees.journal.data.remote.api.JournalService
import tour.donnees.journal.data.remote.response.FrontPageResponse
import tour.donnees.journal.data.remote.response.NewsFromFrontPageResponse
import tour.donnees.journal.domain.repository.FrontPageRepository
import javax.inject.Inject

class FrontPageRepositoryImpl @Inject constructor(
        private val service: JournalService,
        private val frontPageDao: FrontPageDao,
        private val newsDao: NewsDao,
        private val frontPageWithNewsDao: FrontPageWithNewsDao,
        private val mapper: FrontPageCacheMapper,
        private val newsMapper: FrontPageWithNewsCacheMapper,
        private val newsCacheMapper: NewsCacheMapper
    ): BaseRepository(), FrontPageRepository {

    override fun getFrontPageUpdate(token: String): Observable<Result<FrontPageCache>> {
        return service.getFrontPageUpdate(token).map { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { frontPageResponse ->
                        Result.success(
                            mapper.mapFrom(
                                frontPageResponse
                            )
                        )
                    } ?: run {
                        Result.failure(Exception())
                    }
                }
                else -> Result.failure(handling(response.errorBody()))
            }
        }.onErrorReturn {
            Result.failure(it)
        }
    }

    override fun getFrontPageWithNews(token: String): Observable<Result<List<FrontPageWithNewsCache>>> {
        return service.getFrontPage(token).map { response ->
            when {
                response.isSuccessful -> {
                    response.body()?.let { frontPageWithNewsResponse ->
                        insertFrontPage(frontPageWithNewsResponse)
                        insertNews(frontPageWithNewsResponse.news)
                        insertFrontPageWithNews(frontPageWithNewsResponse.news)
                        Result.success(getFrontPageCacheById(frontPageWithNewsResponse.id))
                    } ?: run {
                        Result.failure(Exception())
                    }
                }
                else -> Result.failure(handling(response.errorBody()))
            }
        }.onErrorReturn {
            Result.failure(it)
        }
    }

    private fun insertFrontPage(frontPage: FrontPageResponse) {
        frontPageDao.insert(mapper.mapFrom(frontPage))
    }

    private fun insertFrontPageWithNews(news: List<NewsFromFrontPageResponse>?) {
        news?.let {
            newsMapper.mapFromList(it).map { frontPageWithNews ->
                frontPageWithNewsDao.insert(frontPageWithNews)
            }
        }
    }

    private fun insertNews(newsResponse: List<NewsFromFrontPageResponse>) {
        newsResponse.map {
            it.news?.let { news ->
                newsDao.insert(
                    newsCacheMapper.mapFrom(
                        news
                    )
                )
            }
        }
    }

    private fun getLocalFrontPageCacheById(frontPageId: Int): List<NewsFromFrontPageCache> {
        return frontPageWithNewsDao.getNewsFromFrontPage(frontPageId)
    }

    private fun getFrontPageCacheById(frontPageId: Int): List<FrontPageWithNewsCache> {
        return frontPageWithNewsDao.getFrontPageNews(frontPageId)
    }
}