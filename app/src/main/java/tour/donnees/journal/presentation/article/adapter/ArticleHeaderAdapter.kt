package tour.donnees.journal.presentation.article.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tour.donnees.journal.databinding.HolderArticleHeaderBinding
import tour.donnees.journal.domain.modal.News

import tour.donnees.journal.presentation.base.setImageView

class ArticleHeaderAdapter: ListAdapter<News, ArticleHeaderAdapter.HeaderHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderHolder {
        return HeaderHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HeaderHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }

    class HeaderHolder(private val itemBinding: HolderArticleHeaderBinding):
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(news: News) {
            itemBinding.apply {
                articleHeaderTitle.text = news.title

                articleHeaderSubtitle.apply {
                    when {
                        news.subtitle.isEmpty() -> this.visibility = View.GONE
                        else -> text = news.subtitle
                    }
                }

                articleHeaderPhoto.apply {
                    when {
                        news.imageUrl.isEmpty() -> this.visibility = View.GONE
                        else -> Picasso.get().setImageView(news.imageUrl, this)
                    }
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup): HeaderHolder {
                val itemBinding =
                    HolderArticleHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return HeaderHolder(itemBinding)
            }
        }
    }
}