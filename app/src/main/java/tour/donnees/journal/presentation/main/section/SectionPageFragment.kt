package tour.donnees.journal.presentation.main.section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import tour.donnees.journal.databinding.FragmentSectionPageBinding
import tour.donnees.journal.domain.modal.Category
import tour.donnees.journal.presentation.base.BaseFragment
import tour.donnees.journal.presentation.base.NavigationService
import tour.donnees.journal.presentation.main.MainViewModel

@AndroidEntryPoint
class SectionPageFragment : BaseFragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentSectionPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentSectionPageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(viewModel.mapCategories.values.map { c -> c.name }.toList())

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupView(list: List<String>) {
        binding.apply {
            listView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_expandable_list_item_1, list)

            listView.setOnItemClickListener { _, view, _, _ ->
                val category = (view as TextView).text.toString()
                val c = viewModel.mapCategories[category]
                c?.let {
                    goToCategoryNews(c)
                }
            }
        }
    }

    private fun goToCategoryNews(c: Category) {
       NavigationService.launchSection(requireActivity(), c)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SectionPageFragment()
    }
}
