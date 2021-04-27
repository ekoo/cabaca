package com.eko.cabaca.ui.bookByGenre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eko.cabaca.databinding.BookByGenreFragmentBinding
import com.eko.cabaca.model.BookListByGenreResponseModel
import com.eko.cabaca.util.SUCCESS

class BookByGenreFragment : Fragment() {

    private val binding: BookByGenreFragmentBinding get() = _binding!!
    private var _binding: BookByGenreFragmentBinding? = null
    private lateinit var viewModel: BookByGenreViewModel
    private lateinit var bookAdapter: GenreAdapter
    private val args by navArgs<BookByGenreFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookAdapter = GenreAdapter(this::navigateToDetail)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BookByGenreFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.apply {
            setNavigationOnClickListener { findNavController().navigateUp() }
            title = args.genreTitle
        }

        viewModel = ViewModelProvider(this).get(BookByGenreViewModel::class.java)

        binding.bookList.adapter = bookAdapter
        viewModel.apiCallStatus.observe(viewLifecycleOwner){ status ->
            when(status){
                is SUCCESS -> {
                    val response = status.response as BookListByGenreResponseModel
                    bookAdapter.submitList(response.bookList)
                }
            }
        }

        viewModel.getBooksByGenre(args.genreId)

    }

    private fun navigateToDetail(bookId: Int){
        val direction = BookByGenreFragmentDirections.actionBookByGenreFragmentToBookDetailFragment(bookId)
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}