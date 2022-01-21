package tour.donnees.journal.presentation.article

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import tour.donnees.journal.databinding.ActivityArticleBinding
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.presentation.article.adapter.ArticleFooterAdapter
import tour.donnees.journal.presentation.article.adapter.ArticleHeaderAdapter
import tour.donnees.journal.presentation.article.adapter.ArticleParagraphAdapter
import tour.donnees.journal.presentation.base.BaseActivity
import tour.donnees.journal.presentation.base.NavigationService
import tour.donnees.journal.presentation.base.NavigationService.EXTRA_ARTICLE

@AndroidEntryPoint
class ArticleActivity : BaseActivity<ArticleViewModel, ActivityArticleBinding>() {

    private val viewModel: ArticleViewModel by viewModels()
    private val concatAdapter: ConcatAdapter by lazy { ConcatAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        initializeObservers(viewModel)
        getExtra()

    }

    override fun initializeObservers(viewModel: ArticleViewModel) {
        super.initializeObservers(viewModel)
        viewModel.paragraphs.observe(this, { paragraphs ->
            val adapter = ArticleParagraphAdapter()
            adapter.submitList(paragraphs)
            concatAdapter.addAdapter(adapter)

            val footer = ArticleFooterAdapter().apply {
                submitList(listOf(""))
            }
            concatAdapter.addAdapter(footer)
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

    }

    private fun getExtra() {
        intent.extras?.getParcelable<News>(EXTRA_ARTICLE)?.let { news ->
            val adapter = ArticleHeaderAdapter()
            adapter.submitList(listOf(news))
            concatAdapter.addAdapter(adapter)
            viewModel.getParagraphs(news.id)
            return
        }
    }

    private fun setupView() {
        binding.apply {
            setSupportActionBar(layoutToolbar.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeButtonEnabled(true)
            }
            title = ""
            articleRecyclerView.layoutManager = LinearLayoutManager(
                this@ArticleActivity,
                RecyclerView.VERTICAL,
                false
            )
            articleRecyclerView.adapter = concatAdapter
        }
    }


}
