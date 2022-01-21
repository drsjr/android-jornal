package tour.donnees.journal.domain.usecase

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.domain.modal.Paragraph

interface GetParagraphUseCase {
    fun invoke(token: String, articleId: Int): Observable<Result<List<Paragraph>>>
}