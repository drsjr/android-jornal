package tour.donnees.journal.base

import tour.donnees.journal.core.shared.JournalSharedPreference

class JournalSharedPreferenceMock: JournalSharedPreference {

    private var _token: String = ""

    override fun getToken(): String {
        return _token
    }

    override fun setToken(token: String) {
        _token = token
    }

    override fun removeToken() {
        _token = ""
    }
}