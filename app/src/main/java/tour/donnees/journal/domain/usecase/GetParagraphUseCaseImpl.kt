package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.core.BaseUseCase
import tour.donnees.journal.domain.mapper.ParagraphMapper
import tour.donnees.journal.domain.modal.Paragraph
import tour.donnees.journal.domain.repository.NewsRepository
import javax.inject.Inject

class GetParagraphUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository,
    private val mapper: ParagraphMapper
): BaseUseCase(), GetParagraphUseCase {
    override fun invoke(token: String, articleId: Int): Observable<Result<List<Paragraph>>> {
        return super.invoke(newsRepository.getArticleParagraph(token, articleId).map { result ->
            result.fold({
                Result.success(mapper.mapFromList(it))
            }, {
                Result.failure(it)
            })
        }.onErrorReturn {
            Result.failure(it)
        })
    }
}