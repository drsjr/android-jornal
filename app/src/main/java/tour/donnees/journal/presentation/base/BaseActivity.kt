package tour.donnees.journal.presentation.base

import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T: BaseViewModel, V: ViewBinding>: AppCompatActivity() {

    lateinit var binding: V

    open fun initializeObservers(viewModel: T) {
        viewModel.error.observe(this, { error ->
            when (error.code) {
                401 -> {
                    sendMessage(error.message)
                    logOut()
                    viewModel.removeToken()
                }
                else -> sendMessage(error.message)
            }
        })

        viewModel.networkState.observe(this, { networkState ->
            when(networkState.status) {
                NetworkState.NetworkStateStatus.SUCCESS -> onSuccess(networkState.detail)
                NetworkState.NetworkStateStatus.ERROR -> onError(networkState.detail)
                NetworkState.NetworkStateStatus.RUNNING -> onRunning(networkState.detail)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        NavigationService.launchSingOut(this@BaseActivity)
    }

    fun sendMessage(message: String?) {
        message?.let {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    abstract fun onSuccess(details: String?)

    abstract fun onError(details: String?)

    abstract fun onRunning(details: String?)

    abstract fun isLoading(loading: Boolean)

}