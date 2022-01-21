package tour.donnees.journal.domain.repository

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.data.local.cache.FrontPageCache
import tour.donnees.journal.data.local.cache.NewsAndCategoryCache
import tour.donnees.journal.data.local.cache.NewsCache
import tour.donnees.journal.data.local.cache.ParagraphCache
import tour.donnees.journal.domain.modal.FrontPage
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.domain.modal.Paragraph

interface NewsRepository {

    fun getArticleParagraph(token: String, articleId: Int): Observable<Result<List<ParagraphCache>>>

    fun getNewsByCategory(token: String, categoryId: Int, offset: Int, limit: Int): Observable<Result<List<NewsAndCategoryCache>>>

    fun getNewsByArticleId(token: String, articleId: Int): Observable<Result<NewsCache>>

    fun getLocalNewsAndCategoryByArticleId(articleId: Int): NewsAndCategoryCache
}