package com.eko.cabaca.ui.genreList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eko.cabaca.databinding.ItemGenreBinding
import com.eko.cabaca.model.GenreResponseModel

class GenreListAdapter(
    private val navigateToDetail: (genreId: Int, genreTitle: String) -> Unit
): ListAdapter<GenreResponseModel.GenreModel, GenreListAdapter.GenreViewHolder>(
    object : DiffUtil.ItemCallback<GenreResponseModel.GenreModel>(){
        override fun areItemsTheSame(oldItem: GenreResponseModel.GenreModel, newItem: GenreResponseModel.GenreModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GenreResponseModel.GenreModel, newItem: GenreResponseModel.GenreModel): Boolean {
            return oldItem == newItem
        }
    }
) {

    inner class GenreViewHolder(private val binding: ItemGenreBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: GenreResponseModel.GenreModel){

            with(binding){
                title.text = genre.title
                total.text = genre.count.toString()

                itemView.setOnClickListener {
                    navigateToDetail(genre.id, genre.title)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}