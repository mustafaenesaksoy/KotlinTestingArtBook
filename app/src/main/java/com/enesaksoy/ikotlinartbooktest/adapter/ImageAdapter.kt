package com.enesaksoy.ikotlinartbooktest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.enesaksoy.ikotlinartbooktest.databinding.ImageRowBinding
import javax.inject.Inject

class ImageAdapter @Inject constructor(private val glide : RequestManager) : RecyclerView.Adapter<ImageAdapter.imageHolder>() {

    class imageHolder(val binding: ImageRowBinding) : RecyclerView.ViewHolder(binding.root)

     private var onItemClickListener : ((String) -> Unit)? = null

    private val diffutil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
    private var recyclerlist = AsyncListDiffer(this,diffutil)

    var imagelist : List<String>
    get() = recyclerlist.currentList
    set(value) = recyclerlist.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageHolder {
        val binding = ImageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return imageHolder(binding)
    }

    override fun getItemCount(): Int {
        return imagelist.size
    }

    fun setOnItemClickListener(listener : (String) -> Unit) {
        onItemClickListener = listener
    }
    override fun onBindViewHolder(holder: imageHolder, position: Int) {
        val image = imagelist.get(position)
        glide.load(image).into(holder.binding.searchImage)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(image)
            }
        }
    }
}