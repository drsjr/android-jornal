package tour.donnees.journal.presentation.main.front

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import tour.donnees.journal.databinding.FragmentFrontPageBinding
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.presentation.adapter.MainNewsAdapter
import tour.donnees.journal.presentation.main.MainViewModel
import tour.donnees.journal.presentation.adapter.NewsAdapter
import tour.donnees.journal.presentation.base.BaseFragment
import tour.donnees.journal.presentation.base.NavigationService


@AndroidEntryPoint
class FrontPageFragment : BaseFragment() {

    private var _binding: FragmentFrontPageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private val concatAdapter = ConcatAdapter()
    private val mainNewsAdapter: MainNewsAdapter by lazy { MainNewsAdapter(this@FrontPageFragment::openArticle) }
    private val simpleNewsAdapter: NewsAdapter by lazy { NewsAdapter(this@FrontPageFragment::openArticle) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentFrontPageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        initializeObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFrontPageUpdate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupView() {
        concatAdapter.addAdapter(mainNewsAdapter)
        concatAdapter.addAdapter(simpleNewsAdapter)
        binding.apply {
            frontPageRecycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            frontPageRecycler.adapter = concatAdapter
        }
    }

    private fun initializeObservers() {
        viewModel.frontPageNews.observe(viewLifecycleOwner, { f ->
            mainNewsAdapter.submitList(f
                    .filter { it.place == "main" }
                    .map { it.news }.toList())

            simpleNewsAdapter.submitList(f
                .filter { it.place != "main" }
                .map { it.news }.toList())
        })
    }

    private fun openArticle(news: News) {
        NavigationService.launchArticle(requireActivity(), news)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FrontPageFragment()
    }

}
