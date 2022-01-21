package tour.donnees.journal.core

class Pagination<T>(val id: Int, val limit: Int = 10) {


    var hasMore = true
    var isLoading = false
    var list: MutableSet<T> = mutableSetOf()
    var offset = 0
        private set

    fun add(l: List<T>) {
        when (l.size) {
            0 -> {
                hasMore = false
            }
            else -> {
                list.addAll(l)
                offset += limit
                hasMore = l.isNotEmpty()
            }
        }
        isLoading = false
    }
}