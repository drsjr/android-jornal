package tour.donnees.journal.presentation.splash

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import tour.donnees.journal.databinding.ActivitySplashBinding
import tour.donnees.journal.presentation.base.BaseActivity
import tour.donnees.journal.presentation.base.NavigationService
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeObservers(viewModel)
        time()
    }

    override fun onSuccess(details: String?) {
    }

    override fun onError(details: String?) {
    }

    override fun onRunning(details: String?) {
    }

    override fun isLoading(loading: Boolean) {
    }

    override fun initializeObservers(viewModel: SplashViewModel) {
        super.initializeObservers(viewModel)
        viewModel.isLoggedIn.observe(this, {
            when(it) {
                true -> NavigationService.launchMainPageFromSplash(this@SplashActivity)
                else -> NavigationService.launchLoginFromSplash(this@SplashActivity)
            }
        })
    }

    private fun time() {
        Observable.timer(2000L, TimeUnit.MILLISECONDS).subscribe {
            viewModel.verifyLoggedIn()
        }
    }


}