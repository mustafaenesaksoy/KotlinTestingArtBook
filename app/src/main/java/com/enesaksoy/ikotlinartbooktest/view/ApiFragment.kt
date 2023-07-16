package com.enesaksoy.ikotlinartbooktest.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.enesaksoy.ikotlinartbooktest.R
import com.enesaksoy.ikotlinartbooktest.adapter.ImageAdapter
import com.enesaksoy.ikotlinartbooktest.databinding.FragmentArtApiBinding
import com.enesaksoy.ikotlinartbooktest.util.Status
import com.enesaksoy.ikotlinartbooktest.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ApiFragment @Inject constructor(val adapter : ImageAdapter) :Fragment(R.layout.fragment_art_api) {
    private var binding : FragmentArtApiBinding? = null
    lateinit var viewmodel : ArtViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArtApiBinding.bind(view)
        this.binding = binding
        viewmodel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        var job : Job? = null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewmodel.searchForImage(it.toString())
                    }
                }
            }
        }

        binding.recyclerImageView.adapter = adapter
        binding.recyclerImageView.layoutManager = GridLayoutManager(requireContext(),3)
        adapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewmodel.setselectedImage(it)
        }
        observeOn()
    }

    fun observeOn() {
        viewmodel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map {
                        it.previewURL
                    }

                    adapter.imagelist = urls ?: listOf()
                    binding?.progressBar?.visibility = View.GONE

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    binding?.progressBar?.visibility = View.GONE

                }
                Status.LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE

                }
            }
        })
    }
}