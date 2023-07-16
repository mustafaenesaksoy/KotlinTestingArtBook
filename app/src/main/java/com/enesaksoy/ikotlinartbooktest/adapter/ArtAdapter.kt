package com.enesaksoy.ikotlinartbooktest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.enesaksoy.ikotlinartbooktest.databinding.ArtRowBinding
import com.enesaksoy.ikotlinartbooktest.roomdb.Art
import javax.inject.Inject

class ArtAdapter @Inject constructor(val glide : RequestManager) : RecyclerView.Adapter<ArtAdapter.artHolder>() {
    class artHolder(val binding : ArtRowBinding): RecyclerView.ViewHolder(binding.root)

    val diffutil = object : DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerlist = AsyncListDiffer(this,diffutil)

    var arts : List<Art>
    get() = recyclerlist.currentList
    set(value) = recyclerlist.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): artHolder {
        val binding = ArtRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return artHolder(binding)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    override fun onBindViewHolder(holder: artHolder, position: Int) {
        val art = arts.get(position)
        holder.binding.artrowNameText.text = art.name
        holder.binding.artrowYearText.text = art.year.toString()
        holder.binding.artrowArtistText.text = art.artistname
        glide.load(art.imageUrl).into(holder.binding.artRowImageView)
    }
}