package tour.donnees.journal.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tour.donnees.journal.databinding.HolderMainNewsBinding
import tour.donnees.journal.domain.modal.News
import tour.donnees.journal.presentation.base.gone

class MainNewsAdapter(private val openArticle:((News) -> Unit)): ListAdapter<News, MainNewsAdapter.NewsMainViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsMainViewHolder {
        return NewsMainViewHolder.create(parent, openArticle)
    }

    override fun onBindViewHolder(holder: NewsMainViewHolder, position: Int) {
        holder.binding(getItem(position))
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

    class NewsMainViewHolder(
        private val itemBinding: HolderMainNewsBinding,
        private val openArticle: (News) -> Unit
    ): RecyclerView.ViewHolder(itemBinding.root) {

        fun binding(news: News) {
            itemBinding.apply {
                Picasso
                    .get()
                    .load(news.imageUrl)
                    .into(mainNewsPhoto)

                mainNewsCategory.text = news.category.name
                mainNewsTitle.text = news.title

                mainNewsSubtitle.apply {
                    if (news.subtitle.isNotEmpty())
                        text = news.subtitle
                    else gone()
                }
            }
            itemBinding.root.setOnClickListener {
                openArticle.invoke(news)
            }
        }

        companion object {
            fun create(parent: ViewGroup, openArticle: (News) -> Unit): NewsMainViewHolder {
                val itemBinding = HolderMainNewsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)

                return NewsMainViewHolder(itemBinding, openArticle)
            }
        }
    }
}


