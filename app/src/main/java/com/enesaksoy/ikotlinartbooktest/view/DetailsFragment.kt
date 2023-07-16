package com.enesaksoy.ikotlinartbooktest.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.enesaksoy.ikotlinartbooktest.view.DetailsFragmentDirections
import com.enesaksoy.ikotlinartbooktest.R
import com.enesaksoy.ikotlinartbooktest.databinding.FragmentArtDetailsBinding
import com.enesaksoy.ikotlinartbooktest.util.Status
import com.enesaksoy.ikotlinartbooktest.viewmodel.ArtViewModel
import javax.inject.Inject

class DetailsFragment @Inject constructor(private val glide : RequestManager) : Fragment(R.layout.fragment_art_details) {
    private var binding : FragmentArtDetailsBinding? = null
    lateinit var viewmodel : ArtViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArtDetailsBinding.bind(view)
        this.binding = binding

        viewmodel =ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        binding.imageView.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToApiFragment())
        }
        binding.saveButton.setOnClickListener {
            viewmodel.makeArt(binding.nameText.text.toString(),binding.artistText.text.toString(),binding.yearText.text.toString())
        }

        observeOn()

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    fun observeOn(){
        viewmodel.selectedImageUrl.observe(viewLifecycleOwner, Observer {
            glide.load(it).into(binding!!.imageView)
        })

        viewmodel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"success",Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewmodel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}