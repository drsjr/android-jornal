package tour.donnees.journal.presentation.article.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tour.donnees.journal.databinding.HolderParagraphBinding
import tour.donnees.journal.domain.modal.Paragraph

class ArticleParagraphAdapter: ListAdapter<Paragraph, ArticleParagraphAdapter.ParagraphHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParagraphHolder {
        return ParagraphHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ParagraphHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Paragraph>() {
            override fun areItemsTheSame(oldItem: Paragraph, newItem: Paragraph): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Paragraph, newItem: Paragraph): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ParagraphHolder(private val itemBinding: HolderParagraphBinding):
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(paragraph: Paragraph) {
            itemBinding.apply {
                articleParagraphText.text = paragraph.paragraph
            }
        }

        companion object {
            fun create(parent: ViewGroup): ParagraphHolder {
                val itemBinding =
                    HolderParagraphBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ParagraphHolder(itemBinding)
            }
        }
    }
}