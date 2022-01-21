package tour.donnees.journal.presentation.section

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import tour.donnees.journal.core.Pagination
import tour.donnees.journal.databinding.ActivitySectionBinding
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.presentation.adapter.NewsAdapter
import tour.donnees.journal.presentation.base.*
import tour.donnees.journal.presentation.base.NavigationService.EXTRA_CATEGORY


@AndroidEntryPoint
class SectionPageActivity: BaseActivity<SectionPageViewModel, ActivitySectionBinding>() {

    private lateinit var category: Category

    private val viewModel: SectionPageViewModel by viewModels()
    private val adapter: NewsAdapter by lazy { NewsAdapter(this@SectionPageActivity::openArticle, category.id) }
    private val pagination: Pagination<News> by lazy { Pagination(category.id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeObservers(viewModel)
        getExtra()
        setupView()
        viewModel.getNewsByCategory(categoryId = category.id)
    }

    private fun getExtra() {
        if (intent.hasExtra(EXTRA_CATEGORY)) {
             intent.extras?.getParcelable<Category>(EXTRA_CATEGORY)?.let {
                category = it
            } ?: run {
                 //TODO: show empty page... I need a illustration to my empty state... F...
                finish()
             }
        }
    }

    override fun initializeObservers(viewModel: SectionPageViewModel) {
        super.initializeObservers(viewModel)
        viewModel.news.observe(this, {
            pagination.add(it)
            adapter.submitList(pagination.list.toList())
        })
    }

    override fun onSuccess(details: String?) {
        isLoading(false)
    }

    override fun onError(details: String?) {
        isLoading(false)
    }

    override fun onRunning(details: String?) {
        isLoading(true)
    }
    override fun isLoading(loading: Boolean) {
       binding.apply {
           sectionRecycler.isEnabled = !loading
           loadingInclude.pageLoading.apply {
               if (loading) show() else gone()
           }
       }
    }

    private fun setupView() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        title = category.name

        binding.apply {
            sectionRecycler.layoutManager = LinearLayoutManager(this@SectionPageActivity, RecyclerView.VERTICAL, false)
            sectionRecycler.adapter = ConcatAdapter(adapter)

        sectionRecycler.addOnScrollListener(object : EndlessScroll<News>(pagination){
                override fun call() {
                    when {
                        pagination.hasMore -> {
                            viewModel.getNewsByCategory(
                                categoryId = pagination.id,
                                offset = pagination.offset,
                                limit = pagination.limit
                            )
                        }
                        else -> Unit
                    }

                }
            })

        }
    }

    private fun openArticle(news: News) {
        NavigationService.launchArticle(this, news)
    }
}
