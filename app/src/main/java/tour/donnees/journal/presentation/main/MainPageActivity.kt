package tour.donnees.journal.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import tour.donnees.journal.R
import tour.donnees.journal.databinding.ActivityMainBinding
import tour.donnees.journal.presentation.base.BaseActivity
import tour.donnees.journal.presentation.main.MainViewModel.Companion.RESPONSE_FRONT_PAGE_SUCCESS
import tour.donnees.journal.presentation.main.MainViewModel.Companion.RESPONSE_FRONT_PAGE_UPDATE_SUCCESS
import tour.donnees.journal.presentation.main.MainViewModel.Companion.RESPONSE_GET_ALL_CATEGORY_SUCCESS
import tour.donnees.journal.presentation.main.front.FrontPageFragment
import tour.donnees.journal.presentation.main.section.SectionPageFragment

@AndroidEntryPoint
class MainPageActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()

    private val frontPage: FrontPageFragment by lazy { FrontPageFragment.newInstance() }
    private val sectionPage: SectionPageFragment by lazy { SectionPageFragment.newInstance() }

    private val fragmentList = mapOf(
        R.id.menu_today to frontPage,
        R.id.menu_all_sections to sectionPage)

    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeObservers(viewModel)
        setupView()
        viewModel.getAllCategories()
        fragmentList[R.id.menu_today]?.let { injectFragment(it) }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSuccess(details: String?) {
        when(details) {
            RESPONSE_GET_ALL_CATEGORY_SUCCESS -> {

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

    override fun initializeObservers(viewModel: MainViewModel) {
        super.initializeObservers(viewModel)
    }

    private fun setupView() {
        supportActionBar
        setSupportActionBar(binding.toolbar)
        setupBottomBar()
    }

    private fun setupBottomBar() {
        binding.apply {
            mainBottomNavi.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_today ->  {
                        fragmentList[item.itemId]?.let { injectFragment(it) }
                        true
                    }
                    R.id.menu_all_sections ->  {
                        fragmentList[item.itemId]?.let { injectFragment(it) }
                        true
                    }
                    else -> false
                }
            }

            toolbar.background = mainBottomNavi.background
        }
    }

    private fun injectFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, fragment)
        }
    }

    override fun isLoading(loading: Boolean) {
        /*binding.pageLoading.visibility =
            if (loading) View.VISIBLE else View.GONE*/
    }
}
