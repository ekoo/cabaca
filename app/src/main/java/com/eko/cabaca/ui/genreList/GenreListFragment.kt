package com.eko.cabaca.ui.genreList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eko.cabaca.databinding.GenreListFragmentBinding
import com.eko.cabaca.model.GenreResponseModel
import com.eko.cabaca.ui.home.HomeFragmentDirections
import com.eko.cabaca.util.SUCCESS

class GenreListFragment : Fragment() {

    private val binding: GenreListFragmentBinding get() = _binding!!
    private var _binding: GenreListFragmentBinding? = null
    private lateinit var viewModel: GenreListViewModel
    private lateinit var latestBookAdapter: GenreListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latestBookAdapter = GenreListAdapter(this::navigateToBookByGenre)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GenreListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(GenreListViewModel::class.java)
        binding.genreList.adapter = latestBookAdapter
        viewModel.apiCallStatus.observe(viewLifecycleOwner){ status ->
            when(status){
                is SUCCESS -> {
                    val response = status.response as GenreResponseModel
                    latestBookAdapter.submitList(response.genreList)
                }
            }
        }
    }

    private fun navigateToBookByGenre(genreId: Int, genreTitle: String){
        val direction = HomeFragmentDirections.actionHomeFragmentToBookByGenreFragment(genreId, genreTitle)
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}