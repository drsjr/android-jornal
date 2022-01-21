package tour.donnees.journal.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NoConnectionInterceptor @Inject
constructor(val context: Context): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnectionAvailable()) {

            throw NoConnectionException()
        }
        return chain.proceed(chain.request())
    }

    private fun isConnectionAvailable(): Boolean {
        val manager = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val act = manager.activeNetwork

        return manager.getNetworkCapabilities(act)?.let { nc ->
            return@let nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}