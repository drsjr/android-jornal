package tour.donnees.journal.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.squareup.picasso.Picasso
import tour.donnees.journal.R
import tour.donnees.journal.databinding.HolderNewsBinding
import tour.donnees.journal.databinding.HolderNewsOpinionBinding
import tour.donnees.journal.domain.modal.News

class NewsAdapter(private val openArticle:((News) -> Unit), private val categoryId: Int = 0): ListAdapter<News, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return categoryId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent, openArticle, viewType)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
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

    class NewsViewHolder(
        private val itemBinding: ViewBinding,
        private val openArticle:((News) -> Unit)
    ): RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(news: News) {
            itemBinding.root.setOnClickListener {
                openArticle.invoke(news)
            }

            when(itemBinding) {
                is HolderNewsOpinionBinding -> setHolderNewsOpinion(itemBinding, news)
                else -> setHolderNews(itemBinding as HolderNewsBinding, news)
            }
        }

        private fun setHolderNews(itemBinding: HolderNewsBinding, news: News) {
            itemBinding.apply {
                simpleNewsCategory.text = news.category.name
                simpleNewsTitle.text = news.title

                simpleNewsSubtitle.text = if (news.subtitle.isEmpty()) {
                    simpleNewsSubtitle.visibility = View.GONE
                    ""
                } else news.subtitle

                simpleNewsPhotoReference.text = itemView.context.getText(R.string.references_example)
                if (news.imageUrl.isNotEmpty()) {
                    Picasso
                        .get()
                        .load(news.imageUrl)
                        .into(simpleNewsPhoto)
                } else {
                    simpleNewsPhoto.visibility = View.GONE
                    simpleNewsPhotoReference.visibility = View.GONE
                }
            }
        }

        private fun setHolderNewsOpinion(itemBinding: HolderNewsOpinionBinding, news: News) {
            itemBinding.apply {
                simpleNewsTitle.text = news.title

                simpleNewsSubtitle.text = if (news.subtitle.isEmpty()) {
                    simpleNewsSubtitle.visibility = View.GONE
                    val setCons = ConstraintSet()
                    setCons.clone(itemBinding.newsContainer)
                    setCons.connect(R.id.simple_news_title, ConstraintSet.BOTTOM, R.id.simple_news_photo, ConstraintSet.BOTTOM)
                    setCons.connect(R.id.simple_news_title, ConstraintSet.TOP, R.id.simple_news_photo, ConstraintSet.TOP)
                    setCons.applyTo(itemBinding.newsContainer)
                    ""
                } else news.subtitle

                if (news.imageUrl.isNotEmpty()) {
                    Picasso
                        .get()
                        .load(news.imageUrl)
                        .into(simpleNewsPhoto)
                } else {
                    simpleNewsPhoto.visibility = View.GONE
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup,
                       openArticle:((News) -> Unit), categoryId: Int): NewsViewHolder {

                val itemBinding = if (categoryId == OPINION_CATEGORY)
                    HolderNewsOpinionBinding.inflate(LayoutInflater
                        .from(parent.context), parent, false)
                else HolderNewsBinding.inflate(LayoutInflater
                    .from(parent.context), parent, false)

                return NewsViewHolder(itemBinding, openArticle)
            }

            const val OPINION_CATEGORY = 9
        }
    }
}