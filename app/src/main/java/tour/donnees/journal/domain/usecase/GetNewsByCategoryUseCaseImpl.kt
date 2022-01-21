package tour.donnees.journal.domain.usecase


import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.core.BaseUseCase
import tour.donnees.journal.domain.mapper.NewsAndCategoryMapper
import tour.donnees.journal.domain.mapper.NewsMapper
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.domain.repository.NewsRepository
import javax.inject.Inject


class GetNewsByCategoryUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository,
    private val mapper: NewsAndCategoryMapper
): BaseUseCase(), GetNewsByCategoryUseCase {
    override fun invoke(token: String, categoryId: Int, offset: Int, limit: Int): Observable<Result<List<News>>> {
        return super.invoke(newsRepository.getNewsByCategory(token, categoryId, offset, limit).map { result ->
            result.fold( {
                Result.success(mapper.mapFromList(it))
            }, {
                Result.failure(it)
            })
        }.onErrorReturn {
            Result.failure(it)
        })
    }
}
