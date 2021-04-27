package com.eko.cabaca.ui.latestBook

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eko.cabaca.databinding.LatestBookFragmentBinding
import com.eko.cabaca.model.LatestBookResponseModel
import com.eko.cabaca.ui.home.HomeFragmentDirections
import com.eko.cabaca.util.ERROR
import com.eko.cabaca.util.LOADING
import com.eko.cabaca.util.SUCCESS

class LatestBookFragment : Fragment() {

    private val binding: LatestBookFragmentBinding get() = _binding!!
    private var _binding: LatestBookFragmentBinding? = null
    private lateinit var viewModel: LatestBookViewModel
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookAdapter = BookAdapter(this::navigateToDetail)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LatestBookFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LatestBookViewModel::class.java)
        binding.bookList.adapter = bookAdapter
        viewModel.apiCallStatus.observe(viewLifecycleOwner){ status ->
            when(status){
                is SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    val response = status.response as LatestBookResponseModel
                    bookAdapter.submitList(response.latestBookList)
                }
                is LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ERROR -> {
                    Toast.makeText(requireContext(), status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToDetail(bookId: Int){
        val direction = HomeFragmentDirections.actionHomeFragmentToBookDetailFragment(bookId)
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}