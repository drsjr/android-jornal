package tour.donnees.journal.domain.mapper

import tour.donnees.journal.core.BaseMapper
import tour.donnees.journal.data.local.cache.FrontPageCache
import tour.donnees.journal.data.local.cache.NewsFromFrontPageCache
import tour.donnees.journal.domain.modal.FrontPage
import tour.donnees.journal.domain.modal.FrontPageNews
import java.lang.Exception
import javax.inject.Inject

class FrontPageNewsMapper @Inject
    constructor(private val mapper: NewsMapper): BaseMapper<NewsFromFrontPageCache, FrontPageNews> {

    override fun mapFrom(entity: NewsFromFrontPageCache): FrontPageNews {
        return FrontPageNews(
            place = entity.frontPageItem.place,
            news = mapper.mapFrom(entity.news)
        )
    }

    override fun mapTo(entity: FrontPageNews): NewsFromFrontPageCache {
        throw Exception()
    }

    fun mapFromList(entity: List<NewsFromFrontPageCache>): List<FrontPageNews> {
        return entity.map { mapFrom(it) }
    }

}