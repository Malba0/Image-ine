package com.naw.image_ine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.naw.image_ine.ui.ImageUio
import com.naw.image_ine.ui.ImagesAdapter
import com.naw.image_ine.ui.ImagesViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ImagesFragment : Fragment() {

    private val imagesViewModel: ImagesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = ImagesAdapter()
        imagesViewModel.getImages().observe(viewLifecycleOwner) {
            (recyclerView.adapter as ImagesAdapter).submitList(it as MutableList<ImageUio>)
        }

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            imagesViewModel.getNewImage()
            Snackbar.make(it, getString(R.string.fetch), Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        initDnD(recyclerView)

        imagesViewModel.load()
    }

    private fun initDnD(recyclerView: RecyclerView) {
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or
                    ItemTouchHelper.DOWN or
                    ItemTouchHelper.START or
                    ItemTouchHelper.END,
            ItemTouchHelper.LEFT or
                    ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                (recyclerView.adapter as ImagesAdapter).swapItems(fromPosition, toPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (recyclerView.adapter as ImagesAdapter).removeItem(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onPause() {
        imagesViewModel.saveImages()
        super.onPause()
    }
}
