package tour.donnees.journal.presentation.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tour.donnees.journal.core.Pagination

abstract class EndlessScroll<T>(private val pagination: Pagination<T>): RecyclerView.OnScrollListener()  {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if ((recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            == (recyclerView.adapter?.itemCount ?: 0 ) -1 && !pagination.isLoading) {
                pagination.isLoading = pagination.isLoading
                call()
        }
    }

    abstract fun call()
}
