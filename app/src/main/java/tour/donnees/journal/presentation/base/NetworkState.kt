package tour.donnees.journal.presentation.base

data class NetworkState(val status: NetworkStateStatus, val detail: String?) {
    enum class NetworkStateStatus {
        RUNNING, SUCCESS, ERROR
    }
}