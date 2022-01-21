package tour.donnees.journal.core.shared

interface JournalSharedPreference {

    fun getToken(): String

    fun setToken(token: String)

    fun removeToken()
}