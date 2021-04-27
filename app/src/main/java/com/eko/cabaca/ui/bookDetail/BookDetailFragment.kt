package com.eko.cabaca.ui.bookDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eko.cabaca.databinding.BookDetailFragmentBinding
import com.eko.cabaca.model.BookDetailResponseModel
import com.eko.cabaca.util.SUCCESS
import com.eko.cabaca.util.fixImageUrl
import com.eko.cabaca.util.fromHtml
import com.squareup.picasso.Picasso

class BookDetailFragment : Fragment() {

    private val binding: BookDetailFragmentBinding get() = _binding!!
    private var _binding: BookDetailFragmentBinding? = null
    private lateinit var viewModel: BookDetailViewModel
    private val args by navArgs<BookDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BookDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        viewModel = ViewModelProvider(this).get(BookDetailViewModel::class.java)

        viewModel.apiCallStatus.observe(viewLifecycleOwner){ status ->
            when(status){
                is SUCCESS -> {
                    val result = (status.response as BookDetailResponseModel).result
                    binding.toolbar.title = result.title
                    Picasso.get().load(result.coverUrl.fixImageUrl()).into(binding.bookCover)
                    Picasso.get().load(result.writerByWriterId.userByUserId.photoUrl.fixImageUrl()).into(binding.writerImage)
                    binding.sinopsis.text = result.synopsis.fromHtml()

                }
            }
        }

        viewModel.name.observe(viewLifecycleOwner){ name ->
            binding.writer.text = name
        }

        viewModel.getBooksById(args.bookId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}