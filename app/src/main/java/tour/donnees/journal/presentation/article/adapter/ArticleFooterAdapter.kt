package tour.donnees.journal.presentation.article.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tour.donnees.journal.databinding.HolderArticleFooterBinding
import tour.donnees.journal.databinding.HolderArticleHeaderBinding
import tour.donnees.journal.domain.modal.News

import tour.donnees.journal.presentation.base.setImageView

class ArticleFooterAdapter: ListAdapter<String, ArticleFooterAdapter.FooterHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterHolder {
        return FooterHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FooterHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    class FooterHolder(private val itemBinding: HolderArticleFooterBinding):
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(string: String) {
            itemBinding.apply {
            }
        }

        companion object {
            fun create(parent: ViewGroup): FooterHolder {
                val itemBinding =
                    HolderArticleFooterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return FooterHolder(itemBinding)
            }
        }
    }
}