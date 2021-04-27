package com.eko.cabaca.ui.bookByGenre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eko.cabaca.databinding.ItemBookListBinding
import com.eko.cabaca.model.BookListByGenreResponseModel
import com.eko.cabaca.util.fixImageUrl
import com.squareup.picasso.Picasso

class GenreAdapter(
    private val navigateToDetail: (bookId: Int) -> Unit
): ListAdapter<BookListByGenreResponseModel.BookModel, GenreAdapter.GenreViewHolder>(
    object : DiffUtil.ItemCallback<BookListByGenreResponseModel.BookModel>(){
        override fun areItemsTheSame(oldItem: BookListByGenreResponseModel.BookModel, newItem: BookListByGenreResponseModel.BookModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookListByGenreResponseModel.BookModel, newItem: BookListByGenreResponseModel.BookModel): Boolean {
            return oldItem == newItem
        }
    }
) {

    inner class GenreViewHolder(private val binding: ItemBookListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookListByGenreResponseModel.BookModel){

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}