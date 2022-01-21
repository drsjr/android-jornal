package tour.donnees.journal.core

import tour.donnees.journal.data.remote.response.ApiError
import java.io.IOException
import java.lang.Exception

class BaseException(val error: ApiError): Exception()

class NoConnectionException: IOException()

