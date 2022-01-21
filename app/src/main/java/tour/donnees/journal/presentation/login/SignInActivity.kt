package tour.donnees.journal.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import tour.donnees.journal.databinding.ActivityLoginBinding
import tour.donnees.journal.presentation.base.BaseActivity
import tour.donnees.journal.presentation.base.NavigationService
import tour.donnees.journal.presentation.base.NetworkState
import tour.donnees.journal.presentation.login.SignInViewModel.Companion.SIGN_IN_RESPONSE_SUCCESS
import tour.donnees.journal.presentation.login.SignInViewModel.Companion.USER_ME_RESPONSE_SUCCESS

@AndroidEntryPoint
class SignInActivity : BaseActivity<SignInViewModel, ActivityLoginBinding>() {

    val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeObservers(viewModel)
        setupLayout()
    }

    override fun initializeObservers(viewModel: SignInViewModel) {
        super.initializeObservers(viewModel)
    }

    override fun onSuccess(details: String?) {
        when(details) {
            SIGN_IN_RESPONSE_SUCCESS -> viewModel.getUserInfo()
            USER_ME_RESPONSE_SUCCESS -> {
                NavigationService.launchMainPage(this@SignInActivity, viewModel.userInfo, TAG)
            }
        }
        isLoading(false)
    }

    override fun onError(details: String?) {
        isLoading(false)
    }

    override fun onRunning(details: String?) {
        isLoading(true)
    }

    private fun setupLayout() {

        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.apply {
                    enableSignInButton(username.text?.length ?: 0 > MIN_CHAR_LENGTH
                            && password.text?.length ?: 0 > MIN_CHAR_LENGTH)
                }
            }
        }

        binding.apply {
            username.addTextChangedListener(textWatcher)
            password.addTextChangedListener(textWatcher)
            signIn.setOnClickListener {
                it.isEnabled = false
                viewModel.signIn(
                    username = binding.username.text.toString(),
                    password = binding.password.text.toString()
                )
            }
        }
    }

    private fun enableSignInButton(enable: Boolean) {
        binding.signIn.isEnabled = enable
    }

    override fun isLoading(loading: Boolean) {
        binding.apply {
            loadingInclude.pageLoading.visibility = if (loading) View.VISIBLE else View.GONE
            username.isEnabled = !loading
            password.isEnabled = !loading
            signIn.isEnabled = !loading
        }
    }

    companion object {
        const val TAG = "SignInActivity"
        const val MIN_CHAR_LENGTH = 3
    }
}