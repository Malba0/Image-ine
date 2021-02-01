package com.naw.image_ine.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naw.image_ine.R
import com.squareup.picasso.Picasso
import java.util.*

class ImagesAdapter() : ListAdapter<ImageUio, ImagesAdapter.ImageViewHolder>(ImageDifCallback) {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val authorTv: TextView = itemView.findViewById(R.id.tv_author)
        private val imageIv: ImageView = itemView.findViewById(R.id.iv_image)
        private var currentImage: ImageUio? = null

        fun bind(image: ImageUio) {
            currentImage = image

            authorTv.text = image.author
            Picasso.get()
                .load(image.downloadUrl)
                .fit()
                .into(imageIv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }

    fun swapItems(fromPosition: Int, toPosition: Int) {
        val list = currentList
        val mutList = list.toMutableList()
        Collections.swap(mutList, fromPosition, toPosition)
        submitList(mutList)
    }

    fun removeItem(position: Int) {
        val list = currentList
        val mutList = list.toMutableList()
        mutList.removeAt(position)
        submitList(mutList)
    }

}

object ImageDifCallback : DiffUtil.ItemCallback<ImageUio>() {
    override fun areItemsTheSame(oldItem: ImageUio, newItem: ImageUio): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImageUio, newItem: ImageUio): Boolean {
        return oldItem.id == newItem.id
    }
}