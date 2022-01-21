package tour.donnees.journal.data.base

import com.google.gson.Gson
import okhttp3.ResponseBody
import tour.donnees.journal.core.BaseException
import tour.donnees.journal.data.remote.response.ApiError
import tour.donnees.journal.data.remote.response.ApiErrorResponse
import java.lang.Exception

abstract class BaseRepository {

    fun handling(response: ResponseBody?): Exception {
        val error = response?.string()?.let { body ->
            Gson().fromJson(body, ApiErrorResponse::class.java).detail
        }

        return error?.let {
            when (error.code) {
                HTTP_401_ERROR -> handling401Error(error)
                HTTP_422_ERROR -> handling422Error(error)
                HTTP_404_ERROR -> handling404Error(error)
                else -> handling500Error(error)
            }
        } ?: run {
            Exception()
        }
    }

    open fun handling401Error(error: ApiError) = BaseException(error)

    open fun handling422Error(error: ApiError) = BaseException(error)

    open fun handling404Error(error: ApiError) = BaseException(error)

    open fun handling500Error(error: ApiError) = BaseException(error)

    companion object {
        const val HTTP_401_ERROR = 401
        const val HTTP_422_ERROR = 422
        const val HTTP_404_ERROR = 404
    }

}