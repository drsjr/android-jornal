package tour.donnees.journal.domain.repository

import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.data.local.cache.FrontPageCache
import tour.donnees.journal.data.local.cache.FrontPageWithNewsCache
import tour.donnees.journal.data.local.cache.NewsFromFrontPageCache

interface FrontPageRepository {

    fun getFrontPageUpdate(token: String): Observable<Result<FrontPageCache>>

    fun getFrontPageWithNews(token: String): Observable<Result<List<FrontPageWithNewsCache>>>
}