package tour.donnees.journal.base

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tour.donnees.journal.data.remote.api.JournalService
import tour.donnees.journal.data.remote.response.TokenResponse
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit


/**
 *
 *  https://proandroiddev.com/testing-retrofit-converter-with-mock-webserver-50f3e1f54013
 *
 */

class JournalResponseMock {

    private val webServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(webServer.url("/"))
        .client(client)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JournalService::class.java)

    fun getJournalService() = api

    fun setEndpointContent(jsonUrl: String, code: Int) {
        webServer.enqueueResponse(jsonUrl, code)
    }

    private fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("$fileName.json")

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }

    companion object {

        const val RESPONSE_NEWS_CATEGORY_ID_200 = "news/category/id/http_200"
        const val RESPONSE_NEWS_ID_200 = "news/id/http_200"

        const val RESPONSE_CATEGORY_200 = "category/http_200"
        const val RESPONSE_CATEGORY_ID_200 = "category/id/http_200"

        const val RESPONSE_TOKEN_200 = "token/http_200"
        const val RESPONSE_TOKEN_401 = "token/http_401"

        const val RESPONSE_USERS_ME_200 = "users/me/http_200"

        const val RESPONSE_FRONTPAGE_UPDATE_200 = "frontpage/update/http_200"

        const val RESPONSE_FRONTPAGE_200 = "frontpage/http_200"

        const val RESPONSE_ARTICLE_ID_PARAGRAPHS = "article/id/paragraphs/http_200"

    }

}