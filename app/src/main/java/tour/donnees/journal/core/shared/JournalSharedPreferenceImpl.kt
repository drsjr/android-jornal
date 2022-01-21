package tour.donnees.journal.core.shared

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JournalSharedPreferenceImpl @Inject
    constructor(@ApplicationContext context: Context): JournalSharedPreference {
    private var sharedPreference: SharedPreferences = context.getSharedPreferences("TEST", Context.MODE_PRIVATE)

    override fun getToken(): String {
        return sharedPreference.getString(TOKEN, "").orEmpty()
    }

    override fun setToken(token: String) {
        if (token.isNotEmpty())
            sharedPreference.edit().putString(TOKEN, token).apply()
    }

    override fun removeToken() {
        sharedPreference.edit().remove(TOKEN).apply()
    }

    companion object {
        const val TOKEN = "TOKEN"
    }
}