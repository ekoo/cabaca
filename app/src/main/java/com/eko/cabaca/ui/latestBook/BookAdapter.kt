package com.eko.cabaca.ui.latestBook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eko.cabaca.databinding.ItemBookListBinding
import com.eko.cabaca.model.LatestBookResponseModel
import com.eko.cabaca.util.fixImageUrl
import com.squareup.picasso.Picasso

class BookAdapter(
    private val navigateToDetail: (bookId: Int) -> Unit
): ListAdapter<LatestBookResponseModel.LatestBooksModel, BookAdapter.BookViewHolder>(
    object : DiffUtil.ItemCallback<LatestBookResponseModel.LatestBooksModel>(){
        override fun areItemsTheSame(oldItem: LatestBookResponseModel.LatestBooksModel, newItem: LatestBookResponseModel.LatestBooksModel): Boolean {
            return oldItem.bookId == newItem.bookId
        }

        override fun areContentsTheSame(oldItem: LatestBookResponseModel.LatestBooksModel, newItem: LatestBookResponseModel.LatestBooksModel): Boolean {
            return oldItem == newItem
        }
    }
) {

    inner class BookViewHolder(private val binding: ItemBookListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(book: LatestBookResponseModel.LatestBooksModel){

            with(binding){
                title.text = book.title
                writer.text = book.writerByWriterId.userByUserId.name
                date.text = book.scheduleTask

                Picasso.get().load(book.coverUrl.fixImageUrl()).into(bookCover)

                itemView.setOnClickListener {
                    navigateToDetail(book.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}