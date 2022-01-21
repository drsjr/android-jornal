package tour.donnees.journal.presentation.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment: Fragment() {

    fun isLoading(loading: Boolean) {
        if (activity is BaseActivity<out BaseViewModel, out ViewBinding>) {
            (activity as BaseActivity<out BaseViewModel, out ViewBinding>).isLoading(loading)
        }
    }
}