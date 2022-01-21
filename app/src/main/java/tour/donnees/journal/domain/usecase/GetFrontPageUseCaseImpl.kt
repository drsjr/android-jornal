package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.data.local.cache.NewsFromFrontPageCache
import tour.donnees.journal.domain.core.BaseUseCase
import tour.donnees.journal.domain.mapper.FrontPageNewsMapper
import tour.donnees.journal.domain.mapper.NewsAndCategoryMapper
import tour.donnees.journal.domain.mapper.NewsMapper
import tour.donnees.journal.domain.modal.FrontPageNews
import tour.donnees.journal.domain.repository.FrontPageRepository
import tour.donnees.journal.domain.repository.NewsRepository
import javax.inject.Inject

class GetFrontPageUseCaseImpl @Inject constructor(
    private val frontPageRepository: FrontPageRepository,
    private val newsRepository: NewsRepository,
    private val mapper: FrontPageNewsMapper,
    private val newsAndCategoryMapper: NewsAndCategoryMapper
): BaseUseCase(),  GetFrontPageUseCase {
    override fun invoke(token: String): Observable<Result<List<FrontPageNews>>> {
        return super.invoke(frontPageRepository.getFrontPageWithNews(token).map { result ->
            result.fold({ frontPageList ->
                val mapped = frontPageList.map {
                    val news = newsRepository.getLocalNewsAndCategoryByArticleId(it.articleId)
                    FrontPageNews(
                        place = it.place,
                        news = newsAndCategoryMapper.mapFrom(news)
                    )
                }.toList()
                Result.success(mapped)
            }, {
                Result.failure(it)
            })
        }.onErrorReturn {
            Result.failure(it)
        })
    }
}