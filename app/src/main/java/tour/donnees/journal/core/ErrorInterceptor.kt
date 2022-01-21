package tour.donnees.journal.core

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorInterceptor @Inject
constructor(val context: Context): Interceptor {

    @Inject
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        handlingError(response)
        return handlingError(response)
    }

    private fun handlingError(response: Response): Response {
        return when (response.code) {
            200 -> {
                response
            }
            401-> {
                throw ServiceException()
            }
            500 -> {
                throw ServiceException()
            }
            else -> throw ServiceException()
        }
    }
}