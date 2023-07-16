package com.enesaksoy.ikotlinartbooktest.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enesaksoy.ikotlinartbooktest.view.ArtFragmentDirections
import com.enesaksoy.ikotlinartbooktest.R
import com.enesaksoy.ikotlinartbooktest.adapter.ArtAdapter
import com.enesaksoy.ikotlinartbooktest.databinding.FragmentArtsBinding
import com.enesaksoy.ikotlinartbooktest.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(val adapter : ArtAdapter) : Fragment(R.layout.fragment_arts) {
    private var binding : FragmentArtsBinding? = null
    lateinit var viewmodel : ArtViewModel

    private var swipe = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val selectedart = adapter.arts.get(position)
            viewmodel.deleteArt(selectedart)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArtsBinding.bind(view)
        this.binding = binding

        viewmodel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToDetailsFragment())
        }
        observeOn()
        binding.recyclercountryList.adapter = adapter
        binding.recyclercountryList.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipe).attachToRecyclerView(binding.recyclercountryList)
    }

    fun observeOn(){
        viewmodel.artlist.observe(viewLifecycleOwner, Observer {
            adapter.arts = it
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}