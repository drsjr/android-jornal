package tour.donnees.journal.data.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.FrontPageCache
import tour.donnees.journal.data.local.cache.FrontPageWithNewsCache
import tour.donnees.journal.data.remote.response.FrontPageResponse
import tour.donnees.journal.data.remote.response.FrontPageWithNewsResponse
import tour.donnees.journal.data.remote.response.NewsFromFrontPageResponse
import javax.inject.Inject

class FrontPageWithNewsCacheMapper @Inject constructor():
    BaseMapper<NewsFromFrontPageResponse, FrontPageWithNewsCache> {

    override fun mapFrom(entity: NewsFromFrontPageResponse): FrontPageWithNewsCache {
        return FrontPageWithNewsCache(
            frontPageId = entity.frontPageId,
            articleId = entity.articleId,
            place = entity.place)
    }

    override fun mapTo(entity: FrontPageWithNewsCache): NewsFromFrontPageResponse {
        return NewsFromFrontPageResponse(
                frontPageId = entity.frontPageId,
                articleId = entity.articleId,
                place = entity.place,
                news = null)
    }

    fun mapFromList(entities: List<NewsFromFrontPageResponse>): List<FrontPageWithNewsCache> {
        return entities.map { mapFrom(it) }
    }
}
